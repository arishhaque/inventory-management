package com.inventory.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inventory.exceptions.BaseException;
import com.inventory.models.Product;
import com.inventory.repositories.ProductRepository;
import com.inventory.requests.ProductRequest;
import com.inventory.requests.ProductSearchRequest;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public BaseResponse createUpdate(List<ProductRequest> request) throws BaseException {

        log.info("[Product] Create or update request {}, start time {} ", request, new Date().getTime());
        if(request == null) {
            log.error("[Product] Invalid null Request ");
            throw new BaseException(ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Invalid Request");
        }

        List<String> productIdList = new ArrayList<>();
        request.forEach(productRequest -> {

            Product product = productRepository.findByProductId(productRequest.getProductId());
            if(product == null) {
                if(AppConstants.SUCCESS.equals(create(productRequest)))
                    productIdList.add(productRequest.getProductId());
            } else {
                if(AppConstants.SUCCESS.equals(update(productRequest, product)))
                    productIdList.add(productRequest.getProductId());
            }
        });
        log.info("[Product] Create or update request {}, end time {} ", request, new Date().getTime());
        if(productIdList.isEmpty()) {
            return BaseResponse.builder()
                    .status(AppConstants.ERROR)
                    .message("Error in updating Product Data")
                    .build();
        }
        return BaseResponse.builder()
                .status(AppConstants.SUCCESS)
                .message("Products Updated Successfully")
                .build();
    }

    public String create(ProductRequest request) {

        if(request== null || StringUtils.isBlank(request.getProductId())) {
            log.error("[Product] Error in adding product ");
            return AppConstants.ERROR;
        }
        // Add new product details
        Product product = new Product();
        product.setProductId(request.getProductId());
        if(!StringUtils.isBlank(request.getName()))
            product.setName(request.getName());

        if(request.getMeta() != null)
            product.setMeta(AppUtils.writeValueAsString(request.getMeta()));

        product.setActive(true);
        productRepository.save(product);
        log.info("[Product] New product added successfully with Id {} ", request.getProductId());
        return AppConstants.SUCCESS;
    }


    public String update(ProductRequest request, Product product) {

        if(request == null || product == null || StringUtils.isBlank(request.getProductId())) {
            log.error("[Product] Error in updating product details");
            return AppConstants.ERROR;
        }
        // Update product details
        if(!StringUtils.isBlank(request.getName()))
            product.setName(request.getName());

        if(request.getMeta() != null)
            product.setMeta(AppUtils.writeValueAsString(request.getMeta()));

        if(request.getActive() != null)
            product.setActive(request.getActive());

        productRepository.save(product);
        log.info("[Product] Successfully updated product with Id {} ", request.getProductId());
        return AppConstants.SUCCESS;
    }


    public ProductResponse read(String productId) throws BaseException {

        log.info("[Product] Read product request, start time {} ", new Date().getTime());
        if(StringUtils.isBlank(productId)) {
            log.error("[Product] Invalid null Request ");
            throw new BaseException(ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Invalid Request");
        }
        // Read product from DB
        Product product = productRepository.findByProductId(productId);
        if(product == null) {
            log.error("[Product] No product found with Id {} ",productId);
            throw new BaseException(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Product details not found");
        }
        return buildProductResponse(product);
    }


    public List<ProductResponse> listProducts(Boolean active, Integer pageNo, Integer pageSize) throws BaseException {

        log.info("[Product] Fetch all products request, start time {} ", new Date().getTime());
        List<Product> productList;
        if(pageNo == null || pageNo < 1)
            pageNo = 1;
        if(pageSize == null || pageSize < 1)
            pageSize = 10;

        Integer startRange = (pageNo - 1) * pageSize;
        Integer endRange = pageSize;
        if(active != null)
            productList = productRepository.findByActive(active, startRange, endRange);
        else
            productList = productRepository.findAll();

        if(productList == null || productList.isEmpty()) {
            log.error("[Product] No products found");
            throw new BaseException(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Product details not found");
        }
        // Create product response List
        List<ProductResponse> productResponseList = new ArrayList<>();
        productList.forEach(product -> productResponseList.add(buildProductResponse(product)));
        // sort by product name
        productResponseList.sort((p1, p2) ->  p1.getName().compareTo(p2.getName()));
        log.info("[Product] Fetch all products request, end time {} ", new Date().getTime());
        return productResponseList;
    }


    public BaseResponse deactivate(String productId) throws BaseException {

        log.info("[Product] Deactivate product request, start time {} ", new Date().getTime());
        if(StringUtils.isBlank(productId)) {
            log.error("[Product] Invalid null Request ");
            throw new BaseException(ErrorCodes.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Invalid Request");
        }
        // Read product from DB
        Product product = productRepository.findByProductId(productId);
        if(product == null) {
            log.error("[Product] No product found with Id {} ",productId);
            throw new BaseException(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Product details not found");
        }
        // Deactivate product
        product.setActive(false);
        productRepository.save(product);
        log.info("[Product] Deactivated product with Id {}, end time {} ", productId, new Date().getTime());
        return BaseResponse.builder()
                .status(AppConstants.SUCCESS)
                .message("Product deactivated successfully")
                .build();
    }


    public List<ProductResponse> searchProducts(ProductSearchRequest request) throws BaseException {

        log.info("[Product] Search product request, start time {} ", new Date().getTime());
        StringBuilder searchKey = new StringBuilder("%%");
        if(request != null && !StringUtils.isBlank(request.getSearchKey()))
            searchKey.insert(1, request.getSearchKey());

        // Search product by productId, name
        List<Product> productList = productRepository.searchProducts(searchKey.toString());
        if(productList == null || productList.isEmpty()) {
            log.error("[Product] No products match the search ");
            throw new BaseException(ErrorCodes.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND.value(), "No products match the search");
        }
        List<ProductResponse> productResponseList = new ArrayList<>();
        productList.forEach(product -> productResponseList.add(buildProductResponse(product)));
        log.info("[Product] Search product request end time {} ", new Date().getTime());
        return productResponseList;
    }


    private ProductResponse buildProductResponse(Product product) {

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .meta(!StringUtils.isBlank(product.getMeta()) ?
                        AppUtils.readValue(product.getMeta(), new TypeReference<Map<String, Object>>(){}) : null)
                .active(product.getActive())
                .build();
    }
}
