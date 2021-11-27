package com.inventory.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inventory.exceptions.BaseException;
import com.inventory.models.Product;
import com.inventory.repositories.ProductRepository;
import com.inventory.requests.ProductRequest;
import com.inventory.responses.BaseResponse;
import com.inventory.responses.ProductResponse;
import com.inventory.utils.AppConstants;
import com.inventory.utils.AppUtils;
import com.inventory.utils.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public BaseResponse createUpdate(ProductRequest request) throws BaseException {

        log.info("[Product] Create or update request {} ", request);
        if(request == null || StringUtils.isBlank(request.getProductId())) {
            log.error("[Product] Invalid null Request ");
            throw new BaseException(ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Invalid Request");
        }

        Product product = productRepository.findByProductId(request.getProductId());
        if(product == null) {
            return create(request);
        } else {
            return update(request, product);
        }
    }

    public BaseResponse create(ProductRequest request) throws BaseException {

        if(request != null && !StringUtils.isBlank(request.getProductId())) {

            Product product = new Product();
            product.setProductId(request.getProductId());
            product.setName(request.getName());
            if(request.getMeta() != null)
                product.setMeta(AppUtils.writeValueAsString(request.getMeta()));
            product.setActive(true);

            productRepository.save(product);
            log.info("[Product] New product added successfully with Id {} ", request.getProductId());
            return BaseResponse.builder()
                    .status(AppConstants.SUCCESS)
                    .message("Product added successfully")
                    .build();
        }
        return BaseResponse.builder()
                .status(AppConstants.ERROR)
                .message("Error in adding Product")
                .build();
    }


    public BaseResponse update(ProductRequest request, Product product) throws BaseException {

        if(request != null && product != null
                && !StringUtils.isBlank(request.getProductId())) {

            product.setName(request.getName());
            if(request.getMeta() != null)
                product.setMeta(AppUtils.writeValueAsString(request.getMeta()));
            product.setActive(request.getActive());

            productRepository.save(product);
            log.info("[Product] Successfully updated product with Id {} ", request.getProductId());
            return BaseResponse.builder()
                    .status(AppConstants.SUCCESS)
                    .message("Product updated successfully")
                    .build();
        }
        log.error("[Product] Error in updating product details");
        return BaseResponse.builder()
                .status(AppConstants.ERROR)
                .message("Error in adding Product")
                .build();
    }

    public List<ProductResponse> findAll() throws BaseException {

        List<Product> productList = productRepository.findAll();
        if(productList == null) {
            log.error("[Product] No products found");
            throw new BaseException(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NO_CONTENT.value(), "Product details not found");
        }
        List<ProductResponse> productResponseList = new ArrayList<>();
        productList.forEach(product -> {

            productResponseList.add(ProductResponse.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .meta(!StringUtils.isBlank(product.getMeta()) ?
                            AppUtils.readValue(product.getMeta(), new TypeReference<Map<String, Object>>(){}) : null)
                    .active(product.getActive())
                    .build());
        });
        return productResponseList;
    }
}
