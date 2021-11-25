package com.inventory.services;

import com.inventory.exceptions.BaseException;
import com.inventory.models.Product;
import com.inventory.repositories.ProductRepository;
import com.inventory.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() throws BaseException {

        //throw new BaseException(ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Invalid Request");
        return productRepository.findAll();
    }
}
