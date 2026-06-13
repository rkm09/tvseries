package com.restapi.tvseries.controller;

import com.restapi.tvseries.repository.model.ApiResponse;
import com.restapi.tvseries.repository.model.Series;
import com.restapi.tvseries.repository.model.TvSeries;
import com.restapi.tvseries.service.TvSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    public ResponseEntity<ApiResponse> allTvInfo() {
        ApiResponse response = new ApiResponse(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                tvSeriesService.fetchAll()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/time_frame")
    public ResponseEntity<ApiResponse> tvInfoByPeriod(@RequestParam int startYear,
                                                      @RequestParam(defaultValue = "-1") int endYear) {
        ApiResponse response = new ApiResponse(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                tvSeriesService.fetchByPeriod(startYear, endYear)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
