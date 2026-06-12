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

/*
Use the HTTP GET method to fetch information about recent TV shows. Query the endpoint https://jsonmock.hackerrank.com/api/tvseries to retrieve all records. The results are paginated and can be accessed by appending ?page=num to the query string, where num is the page number.

The response is a JSON object containing the following fields:

Page: current page of results (Number)
per_page: maximum number of results per page (Number)
Total: total number of results (Number)
Total_pages: total number of pages with results (Number)
Data: an array of TV series records

Each TV series in the data array has the following structure:
{
	“name” : “Game Of Thrones”,
	“runtime_of_series”: “(2011-2019)”,
	“certificate”: “A”,
	“runtime_of_episodes”: “57 min”,
	“genre”: “Action, Adventure, Drama”,
	“imdb_rating”: 9.3,
	“overview”: “Nine noble families fight for control over the lands of Westeros, while an 		ancient enemy returns after being dormant for millennia.”,
	“no_of_votes”: 1773458,
	“id”: 1
}

In data, each tv series has the following schema:
name: (String)
runtime_of_series: years the show is in production (String)
Certificate: rating (String)
runtime_of_episodes: average length per episode in minutes (String)
Genre: genre (String)
imdb_rating: average viewer rating (Number)
Overview: short description (String)
no_of_votes: how many votes were cast on imdb (Number)
Id: unique identifier (Number)

There are 4 possible forms of runtime_of_series.
(2020-2021) - the first and last years of production are shown
(2020- ) - The show is still in production
(2020) - The show was only produced for one year
Entries may have (I) or (II) followed by one of the above formats.


Given a start year and an end year, return a list of the names of all tv series that started production in startYear or later and ended production in endYear or  earlier. If the endYear is -1, the shows should still be in production. Sort the list in alphabetical order.

Function Description:

Complete the function showsInProduction in the editor with the following

Int startYear: the earliest year of production
Int endYear: the latest year of production  or -1

Return :

string[]: the sorted list of names of shows in production during the time period
 */