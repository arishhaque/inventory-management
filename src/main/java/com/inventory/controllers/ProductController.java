package com.inventory.controllers;

import com.inventory.exceptions.BaseException;
import com.inventory.models.Product;
import com.inventory.requests.ProductRequest;
import com.inventory.requests.ProductSearchRequest;
import com.inventory.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "Product Controller")
@RequestMapping(value = "/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized request to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    @ApiOperation(value = "Add or update products")
    @PostMapping(value = "/create-update", produces = "application/json")
    public ResponseEntity saveProducts(@RequestBody List<ProductRequest> request) throws BaseException {
        log.info("API Request: /product/create-update ");
        return ResponseEntity.ok().body(productService.createUpdate(request));
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized request to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    @ApiOperation(value = "Search Products", response = ResponseEntity.class)
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchProducts(@RequestBody ProductSearchRequest request) throws BaseException {
        log.info("API Request: /product/list ");
        return ResponseEntity.ok().body(productService.searchProducts(request));
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized request to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    @ApiOperation(value = "List all Products", response = ResponseEntity.class)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listProducts(@RequestParam String active) throws BaseException {
        log.info("API Request: /product/list ");
        return ResponseEntity.ok().body(productService.listProducts(active));
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized request to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    @ApiOperation(value = "Search a product with an Id",response = Product.class)
    @GetMapping(value = "/read/{productId}", produces = "application/json")
    public ResponseEntity read(@PathVariable String productId) throws BaseException {
        log.info("API Request: /product/read ");
        return ResponseEntity.ok().body(productService.read(productId));
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized request to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    })
    @ApiOperation(value = "Deactivate a product")
    @GetMapping(value="/deactivate/{productId}", produces = "application/json")
    public ResponseEntity deactivate(@PathVariable String productId) throws BaseException {
        log.info("API Request: /product/deactivate ");
        return ResponseEntity.ok().body(productService.deactivate(productId));
    }

}
