package com.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BaseExceptionResponse {

    private int code;
    private String message;
}
