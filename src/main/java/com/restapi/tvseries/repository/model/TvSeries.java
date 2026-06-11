package com.restapi.tvseries.repository.model;

public record TvSeries(int page, int perPage, int total, int totalPages, String[] data) { }
