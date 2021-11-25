package com.inventory.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(value = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok().build();
    }
}
