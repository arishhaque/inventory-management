package com.inventory.exceptions;

import com.inventory.utils.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice(basePackages = "com.inventory.controllers")
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseExceptionResponse> handleException(Exception e) throws Exception {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCodes.GENERIC_ERROR_CODE);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionResponse> handleException(BaseException e) throws Exception {
        return error(e, e.getHttpErrorCode(), e.getErrorCode());
    }

    private ResponseEntity<BaseExceptionResponse> error(final Exception e, final int httpStatus, final int errorCode) {
        final String message = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        return new ResponseEntity<>(new BaseExceptionResponse(errorCode, message), HttpStatus.valueOf(httpStatus));
    }
}
