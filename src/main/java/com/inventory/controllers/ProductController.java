package com.inventory.controllers;

import com.inventory.exceptions.BaseException;
import com.inventory.models.Product;
import com.inventory.requests.ProductRequest;
import com.inventory.services.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
@Api(value = "Product Controller")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "List all Products", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized request to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not Found")
    }
    )
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllProducts() throws BaseException {

        return ResponseEntity.ok().body(productService.findAll());
    }

    @ApiOperation(value = "Search a product with an Id",response = Product.class)
    @GetMapping(value = "/show/{id}", produces = "application/json")
    public ResponseEntity showProduct(@PathVariable Integer id) throws BaseException {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @ApiOperation(value = "Add or update a new product")
    @PostMapping(value = "/create-update", produces = "application/json")
    public ResponseEntity saveProduct(@RequestBody ProductRequest request) throws BaseException {

        return ResponseEntity.ok().body(productService.createUpdate(request));
    }

    @ApiOperation(value = "Delete a product")
    @PostMapping(value="/delete/{id}", produces = "application/json")
    public ResponseEntity delete(@PathVariable Integer id){
        //productService.deleteProduct(id);
        return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
    }

}
