package com.restapi.tvseries.controller;

import com.restapi.tvseries.repository.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greet(@RequestParam(defaultValue = "Multiverse") String name) {
        return new Greeting(counter.incrementAndGet(), template.formatted(name));
    }
}


// http://localhost:8080/greeting?name=User.