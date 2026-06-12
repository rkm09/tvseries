package com.restapi.tvseries.controller;

import com.restapi.tvseries.repository.model.Series;
import com.restapi.tvseries.repository.model.TvSeries;
import com.restapi.tvseries.service.TvSeriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tvseries")
public class TvSeriesController {

    private final TvSeriesService tvSeriesService;

    public TvSeriesController(TvSeriesService tvSeriesService) {
        this.tvSeriesService = tvSeriesService;
    }

    @GetMapping
    public TvSeries tvInfoByPage(@RequestParam(defaultValue = "1") int page) {
        return tvSeriesService.fetchTvSeriesByPage(page);
    }

    @GetMapping("/all")
    public List<Series> allTvInfo() {
        return tvSeriesService.fetchAll();
    }


    @GetMapping("/time_frame")
    public List<Series> tvInfoByPeriod(@RequestParam int startYear,
                                      @RequestParam(defaultValue = "-1") int endYear) {
        return tvSeriesService.fetchByPeriod(startYear, endYear);
    }
}
