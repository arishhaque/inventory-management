package com.inventory.tests.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inventory.configs.AppConfig;
import com.inventory.controllers.ProductController;
import com.inventory.requests.ProductRequest;
import com.inventory.responses.BaseResponse;
import com.inventory.responses.ProductResponse;
import com.inventory.services.ProductService;
import com.inventory.utils.AppConstants;
import com.inventory.utils.AppUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController = new ProductController();

    @Mock
    private ProductService productService;

    @Autowired
    private AppConfig appConfig;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void createUpdateTest() throws Exception {

        List<ProductRequest> productRequestList = new ArrayList<>();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId("a17869c");
        productRequest.setName("Gone with the wind");
        productRequest.setActive(true);

        Mockito.when(productService.createUpdate(any())).thenReturn(new BaseResponse(AppConstants.STATUS_SUCCESS,
                "Products Updated Successfully"));

        ResultActions resultActions = this.mockMvc
                .perform(post( appConfig.getServer().getServlet().getContextPath()
                        +"/product/create-update")
                        .contextPath(appConfig.getServer().getServlet().getContextPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AppUtils.writeValueAsString(productRequestList))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createUpdateNullOrEmptyRequestTest() throws Exception {

        Mockito.when(productService.createUpdate(any())).thenReturn(new BaseResponse(AppConstants.STATUS_SUCCESS,
                "Products Updated Successfully"));

        ResultActions resultActions = this.mockMvc
                .perform(post( appConfig.getServer().getServlet().getContextPath()
                        +"/product/create-update")
                        .contextPath(appConfig.getServer().getServlet().getContextPath())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void listProductsTest() throws Exception {

        Mockito.when(productService.listProducts(any(), any(), any())).thenReturn(new ArrayList<>());
        ResultActions resultActions = this.mockMvc
                .perform(get( appConfig.getServer().getServlet().getContextPath()
                        +"/product/list?active=true&pageNo=1&pageSize=100")
                        .contextPath(appConfig.getServer().getServlet().getContextPath())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void readProductsTest() throws Exception {

        ProductResponse productResponse = new ProductResponse("a17869c", "Gone with the wind",
                new HashMap<>(), true);

        Mockito.when(productService.read(any())).thenReturn(productResponse);
        ResultActions resultActions = this.mockMvc
                .perform(get( appConfig.getServer().getServlet().getContextPath()
                        +"/product/read/a17869c")
                        .contextPath(appConfig.getServer().getServlet().getContextPath())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Map<String, Object> responseMap = AppUtils.readValue(resultActions
                .andReturn().getResponse().getContentAsString(), new TypeReference<Map<String, Object>>(){});

        Assertions.assertEquals("a17869c", responseMap.get("productId"));

    }
}
