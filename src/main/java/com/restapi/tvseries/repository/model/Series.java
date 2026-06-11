package com.restapi.tvseries.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Series(
    String name,
    @JsonProperty("runtime_of_series") String runTimeSeries,
    String certificate,
    @JsonProperty("runtime_of_episodes") String runTimeEpisodes,
    String genre,
    @JsonProperty("imdb_rating") double imdbRating,
    String overview,
    @JsonProperty("no_of_votes") int voteCount,
    String id
) { }
