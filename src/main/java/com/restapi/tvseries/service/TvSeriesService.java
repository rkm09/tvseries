package com.restapi.tvseries.service;

import com.restapi.tvseries.repository.model.TvSeries;

public interface TvSeriesService {
    TvSeries fetchTvSeries(int page);
}
