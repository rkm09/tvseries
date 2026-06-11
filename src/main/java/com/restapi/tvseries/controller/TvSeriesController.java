package com.restapi.tvseries.controller;

import com.restapi.tvseries.repository.model.TvSeries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TvSeriesController {
    @GetMapping("/tvseries")
    public TvSeries fetchTvSeries(@RequestParam(defaultValue = "1") int page) {

        return new TvSeries();
    }
}
