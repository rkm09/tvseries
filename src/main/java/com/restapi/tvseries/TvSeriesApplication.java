package com.restapi.tvseries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class TvSeriesApplication {

	private static final Logger log = LoggerFactory.getLogger(TvSeriesApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TvSeriesApplication.class, args);
	}

	@Bean
	public RestClient restClient(RestClient.Builder builder) {
		log.info("calling rest client");
		return builder
				.baseUrl("https://jsonmock.hackerrank.com/")
				.requestInterceptor((request, body, execution) -> {
					log.info("Sending external request to: {}", request.getURI());
					long startTime = System.currentTimeMillis();
					var response = execution.execute(request, body);
					long duration = System.currentTimeMillis() - startTime;
					log.info("Received response with status {} in {} ms", response.getStatusCode(),
							duration);
					return response;
				})
				.build();
	}

}


/*
 	@Bean
	@Profile("!test")
	public ApplicationRunner run(RestClient.Builder builder) {
		RestClient restClient = builder
				.baseUrl("https://jsonmock.hackerrank.com/")
				.build();

		return args -> {
			TvSeries tvSeries = restClient
					.get().uri("api/tvseries")
					.retrieve()
					.body(TvSeries.class);
            assert tvSeries != null;
            log.info(tvSeries.toString());
		};
	}
 */