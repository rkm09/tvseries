package com.restapi.tvseries.repository.model;

import java.time.LocalDateTime;
import java.util.List;

public record ApiResponse(
        LocalDateTime timestamp,
        int status,
        List<Series> response
        )
{ }
