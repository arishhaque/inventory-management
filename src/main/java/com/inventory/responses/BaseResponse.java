package com.inventory.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResponse {

    private String status;
    private String message;
}
