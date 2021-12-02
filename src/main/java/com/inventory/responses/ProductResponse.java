package com.inventory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
public class ProductResponse {

    private String productId;
    private String name;
    private Map<String, Object> meta;
    private Boolean active;
}
