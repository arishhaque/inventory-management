package com.inventory.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class ProductResponse {

    private String productId;
    private String name;
    private Map<String, Object> meta;
    private Boolean active;
}
