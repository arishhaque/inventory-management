package com.inventory.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class BaseResponse {

    private String status;
    private String message;
}
