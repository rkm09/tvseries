package com.restapi.tvseries;

import com.restapi.tvseries.controller.TvSeriesController;
import com.restapi.tvseries.repository.model.Series;
import com.restapi.tvseries.repository.model.TvSeries;
import com.restapi.tvseries.service.TvSeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TvSeriesController.class)
public class TvSeriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean  // mocks the service and places it in the application context
    private TvSeriesService tvSeriesService;

    private Series sampleShow1;
    private Series sampleShow2;

    @BeforeEach
    void setUp() {
        sampleShow1 = new Series(
                "Breaking Bad", "(2008-2013)", "18+", "49 min",
                "Crime, Drama", 9.5, "A chemistry teacher turns bad.", 2000000, "1"
        );

        sampleShow2 = new Series(
                "Better Call Saul", "(2015-2022)", "18+", "46 min",
                "Crime, Drama", 8.9, "The trials of lawyer Saul Goodman.", 600000, "2"
        );
    }

    @Test
    @DisplayName("GET /api/tvseries - Should return TvSeries wrapper object for a given page")
    void shouldReturnTvSeriesByPage() throws Exception {
        TvSeries mockTvSeriesWrapper = new TvSeries(
                1, 2, 2, 1, List.of(sampleShow1, sampleShow2)
        );
        when(tvSeriesService.fetchTvSeriesByPage(1)).thenReturn(mockTvSeriesWrapper);

        mockMvc.perform(get("/api/tvseries")
                        .param("page", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.total_pages").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Breaking Bad"))
                .andExpect(jsonPath("$.data[1].name").value("Better Call Saul"));

    }

    @Test
    @DisplayName("GET /api/tvseries/all - Should wrap master list inside API response correctly")
    void shouldReturnAllTvSeriesWrappedInApiResponse() throws Exception {
        List<Series> allShows = List.of(sampleShow1, sampleShow2);
        when(tvSeriesService.fetchAll()).thenReturn(allShows);

        mockMvc.perform(get("/api/tvseries/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.response[0].name").value("Breaking Bad"))
                .andExpect(jsonPath("$.response[1].name").value("Better Call Saul"));
    }

    @Test
    @DisplayName("GET /api/tvseries/time_frame - Should pass timeframe params and return matching API wrapper")
    void shouldReturnTvSeriesByPeriodFiltered() throws Exception{
//        pretend only show1 is falls in the category
        List<Series> filteredShows = List.of(sampleShow1);
        when(tvSeriesService.fetchByPeriod(anyInt(), anyInt())).thenReturn(filteredShows);

        mockMvc.perform(get("/api/tvseries/time_frame")
                        .param("startYear", "2015")
                        .param("endYear", "2019")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath(".response[0].name").value("Breaking Bad"))
                .andExpect(jsonPath(".response[0].runtime_of_series").value("(2008-2013)"));
    }
}
