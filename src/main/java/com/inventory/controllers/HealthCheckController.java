package com.inventory.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check Controller")
public class HealthCheckController {

    @Operation(summary = "Add or update products")
    @GetMapping(value = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok().body("success");
    }
}
