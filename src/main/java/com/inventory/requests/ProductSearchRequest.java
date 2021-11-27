package com.inventory.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSearchRequest {

    @NotNull
    @JsonProperty("searchKey")
    private String searchKey;
}
