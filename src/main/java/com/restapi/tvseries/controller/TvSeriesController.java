package com.restapi.tvseries.controller;

import com.restapi.tvseries.repository.model.TvSeries;
import com.restapi.tvseries.service.TvSeriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TvSeriesController {

    private final TvSeriesService tvSeriesService;

    public TvSeriesController(TvSeriesService tvSeriesService) {
        this.tvSeriesService = tvSeriesService;
    }

    @GetMapping("/tvseries")
    public TvSeries tvInfo(@RequestParam(defaultValue = "1") int page) {
        return tvSeriesService.fetchTvSeries(page);
    }
}
