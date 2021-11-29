package com.inventory.controllers;

import com.inventory.exceptions.BaseException;
import com.inventory.requests.ProductRequest;
import com.inventory.requests.ProductSearchRequest;
import com.inventory.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Product Controller")
@RequestMapping(value = "/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;



    @Operation(summary = "Add or update products")
    @PostMapping(value = "/create-update", produces = "application/json")
    public ResponseEntity saveProducts(@RequestBody List<ProductRequest> request) throws BaseException {
        log.info("API Request: /product/create-update ");
        return ResponseEntity.ok().body(productService.createUpdate(request));
    }


    @Operation(summary = "Search Products")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchProducts(@RequestBody ProductSearchRequest request) throws BaseException {
        log.info("API Request: /product/list ");
        return ResponseEntity.ok().body(productService.searchProducts(request));
    }


    @Operation(summary = "List all Products")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listProducts(@RequestParam String active) throws BaseException {
        log.info("API Request: /product/list ");
        return ResponseEntity.ok().body(productService.listProducts(active));
    }


    @Operation(summary = "Search a product with an Id")
    @GetMapping(value = "/read/{productId}", produces = "application/json")
    public ResponseEntity read(@PathVariable String productId) throws BaseException {
        log.info("API Request: /product/read ");
        return ResponseEntity.ok().body(productService.read(productId));
    }


    @Operation(summary = "Deactivate a product")
    @GetMapping(value="/deactivate/{productId}", produces = "application/json")
    public ResponseEntity deactivate(@PathVariable String productId) throws BaseException {
        log.info("API Request: /product/deactivate ");
        return ResponseEntity.ok().body(productService.deactivate(productId));
    }

}
