package com.restapi.tvseries.service;

import com.restapi.tvseries.repository.model.TvSeries;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TvSeriesServiceImpl implements TvSeriesService {
    private final RestClient restClient;

//    spring automatically injects rest client bean here
    public TvSeriesServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public TvSeries fetchTvSeries(int page) {
        return restClient
                .get()
                .uri(uriBuilder ->  uriBuilder
                        .path("/api/tvseries")
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .body(TvSeries.class);
    }
}
