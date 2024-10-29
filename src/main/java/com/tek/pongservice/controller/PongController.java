package com.tek.pongservice.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.tek.pongservice.properties.RateLimiterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PongController {

    private static final Logger logger = LoggerFactory.getLogger(PongController.class);


    @Autowired
    private RateLimiter rateLimiter;

    @Autowired
    private RateLimiterProperties properties;

    /*
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private LocalDateTime lastResetTime = LocalDateTime.now();
    */


    @GetMapping("/pong")
    public ResponseEntity<String> getPong() {
        boolean acquired = rateLimiter.tryAcquire(properties.getTimeout(), TimeUnit.SECONDS);
        if(acquired) {
            //TODO verify request
            //...
            logger.info("Responding with 'World' at {}", LocalDateTime.now());
            return ResponseEntity.ok("World");
        }
        logger.warn("Throttling request at {}", LocalDateTime.now());
        return ResponseEntity.status(429).body("Too many requests, please try again later.");
    }


    /*
    @GetMapping("/pong2")
    public ResponseEntity<String> getPong2() {
        LocalDateTime now = LocalDateTime.now();
        long secondsSinceLastReset = ChronoUnit.SECONDS.between(lastResetTime, now);
        if (secondsSinceLastReset > 1) {
            lastResetTime = now;
            requestCount.set(0);
        }
        if (requestCount.incrementAndGet() > 1) {
            logger.warn("Throttling request at {}", now);
            return ResponseEntity.status(429).body("Too Many Requests");
        }
        logger.info("Responding with 'World' at {}", now);
        return ResponseEntity.ok("World");
    }*/

}
