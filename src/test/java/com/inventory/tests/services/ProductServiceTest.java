package com.inventory.tests.services;

import com.inventory.exceptions.BaseException;
import com.inventory.models.Product;
import com.inventory.repositories.ProductRepository;
import com.inventory.requests.ProductRequest;
import com.inventory.responses.BaseResponse;
import com.inventory.responses.ProductResponse;
import com.inventory.services.ProductService;
import com.inventory.utils.AppConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService = new ProductService();

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void productCreateTest() throws BaseException {

        List<ProductRequest> productRequestList = new ArrayList<>();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId("a17869c");
        productRequest.setName("Gone with the wind");
        productRequest.setActive(true);
        productRequestList.add(productRequest);

        Mockito.when(productRepository.findByProductId(any()))
                .thenReturn(null);

        BaseResponse response = productService.createUpdate(productRequestList);

        Mockito.verify(productRepository, Mockito.times(1)).findByProductId(any());
        Assertions.assertEquals(AppConstants.STATUS_SUCCESS, response.getStatus());
    }

    @Test
    public void productUpdateTest() throws BaseException {

        List<ProductRequest> productRequestList = new ArrayList<>();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId("a17869c");
        productRequest.setName("Gone with the wind");
        productRequest.setActive(true);
        productRequestList.add(productRequest);

        Mockito.when(productRepository.findByProductId(any()))
                .thenReturn(new Product("a17869c", "Gone with the wind", "", true));

        BaseResponse response = productService.createUpdate(productRequestList);

        Mockito.verify(productRepository, Mockito.times(1)).findByProductId(any());
        Assertions.assertEquals(AppConstants.STATUS_SUCCESS, response.getStatus());
    }

    @Test
    public void productReadTest() throws BaseException {

        Mockito.when(productRepository.findByProductId(any()))
                .thenReturn(new Product("a17869c", "Gone with the wind", "", true));

        ProductResponse response = productService.read("a17869c");

        Mockito.verify(productRepository, Mockito.times(1)).findByProductId(any());

        Assertions.assertEquals("a17869c", response.getProductId());
        Assertions.assertEquals("Gone with the wind", response.getName());
    }

    @Test
    public void productListTest() throws BaseException {

        List<Product> productList = new ArrayList<>();
        Product product1 = new Product("a17869c", "Gone with the wind", "", true);
        Product product2 = new Product("a169869z", "Normandy Park", "", true);
        productList.add(product1);
        productList.add(product2);

        Mockito.when(productRepository.findByActive(any(), any(), any()))
                .thenReturn(productList);

        Mockito.when(productRepository.findAll()).thenReturn(productList);

        List<ProductResponse> productResponseList = productService.listProducts(true, 1, 10);

        Mockito.verify(productRepository, Mockito.atMost((1))).findByProductId(any());
        Mockito.verify(productRepository, Mockito.atMost((1))).findAll();

        Assertions.assertEquals(Integer.valueOf(2), productResponseList.size());
    }
}
