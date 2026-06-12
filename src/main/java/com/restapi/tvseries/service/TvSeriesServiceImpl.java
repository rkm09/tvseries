package com.restapi.tvseries.service;

import com.restapi.tvseries.repository.model.Series;
import com.restapi.tvseries.repository.model.TvSeries;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class TvSeriesServiceImpl implements TvSeriesService {
    private final RestClient restClient;

//    spring automatically injects rest client bean here
    public TvSeriesServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public TvSeries fetchTvSeriesByPage(int page) {
        return restClient
                .get()
                .uri(uriBuilder ->  uriBuilder
                        .path("/api/tvseries")
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .body(TvSeries.class);
    }

    @Override
    public List<Series> fetchAll() {
        List<Series> allSeriesMasterList = new ArrayList<>();
        TvSeries firstPage = fetchTvSeriesByPage(1);
        if (firstPage != null && firstPage.data() != null) {
            allSeriesMasterList.addAll(firstPage.data());
            int totalPages = firstPage.totalPages();
//            high performance parallel fetch
            List<Series> remainingData = IntStream.rangeClosed(2, totalPages)
                    .parallel()
                    .mapToObj(this::fetchTvSeriesByPage)
                    .filter(page -> page != null  && page.data() != null)
                    .flatMap(page -> page.data().stream())
                    .toList();

            allSeriesMasterList.addAll(remainingData);
        }

        return allSeriesMasterList;
    }

    @Override
    public List<Series> fetchByPeriod(int startYear, int endYear) {
        List<Series> allSeriesMasterList = fetchAll();
        return  allSeriesMasterList
                .parallelStream()
                .filter(series -> {
                    String runTime = series.runTimeSeries();
                    if(runTime == null || runTime.isBlank()) return false;
                    return processPeriod(startYear, endYear, runTime);
                })
                .sorted(Comparator.comparing(Series::name)) // sort alphabetically
                .toList();
    }

    private boolean processPeriod(int startYear, int endYear, String runTime) {
        boolean matchesStart = false, matchesEnd = false;
        Pattern pattern = Pattern.compile(
                "\\((\\d{4})\\s*-\\s*(\\d{4})?\\)|\\((\\d{4})\\)"
        );

        Matcher matcher = pattern.matcher(runTime);
        if (matcher.find()) {
            int showStart, showEnd;
//            group 3: matches single year format (2024)
            if (matcher.group(3) != null) {
                showStart = Integer.parseInt(matcher.group(3));
                showEnd = showStart;
            } else {
//                group 1: matches start year in the range (2020-)
                showStart = Integer.parseInt(matcher.group(1));
//                group 2: matches end year if present(2020-2024), else it is still running (2020-)
                String endGroup = matcher.group(2);
                showEnd = (endGroup != null) ? Integer.parseInt(endGroup) : -1;
            }
//            apply the range logic
//            condition A: must start in startYear or later
            matchesStart = showStart >= startYear;
//            condition B: handle end year logic
//            if currently in production, showEnd = -1 else must end in endYear or earlier
            if (endYear == -1)
                matchesEnd = (showEnd == -1);
            else
                matchesEnd = ((showEnd != -1) && (showEnd <= endYear));
        }

        return matchesStart && matchesEnd;
    }

}


/*
@Override
    public List<Series> fetchAll() {
        List<Series> allSeriesMasterList = new ArrayList<>();
        TvSeries firstPage = fetchTvSeriesByPage(1);
        if (firstPage != null && firstPage.data() != null) {
            allSeriesMasterList.addAll(firstPage.data());
            int totalPages = firstPage.totalPages();
            for (int page = 2; page <= totalPages; page++) {
                TvSeries currentPage = fetchTvSeriesByPage(page);
                if (currentPage != null && currentPage.data() != null) {
                    allSeriesMasterList.addAll(currentPage.data());
                }
            }
        }

        return allSeriesMasterList;
    }
 */