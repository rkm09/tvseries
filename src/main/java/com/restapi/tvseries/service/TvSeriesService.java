package com.restapi.tvseries.service;

import com.restapi.tvseries.repository.model.Series;
import com.restapi.tvseries.repository.model.TvSeries;

import java.util.List;

public interface TvSeriesService {
    TvSeries fetchTvSeriesByPage(int page);
    List<Series> fetchAll();
    List<Series> fetchByPeriod(int startYear, int endYear);
}
