package com.restapi.tvseries.config;

import com.restapi.tvseries.TvSeriesApplication;
import com.restapi.tvseries.exception.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    private static final Logger log = LoggerFactory.getLogger(ClientConfig.class);

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://jsonmock.hackerrank.com/")
                .defaultStatusHandler(HttpStatusCode::isError,
                        ((request, response) -> {
                            log.error("API error detected: {}", response.getStatusCode());
                            throw new ExternalApiException("The downstream HackerRank API is currently unavailable.");
                        }))
                .build();
    }
}
