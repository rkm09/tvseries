package com.restapi.tvseries;

import com.restapi.tvseries.service.TvSeriesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class TvSeriesApplicationTests {

	@MockitoBean
	private TvSeriesService tvSeriesService;

	@Test
	void contextLoads() {
	}

}
