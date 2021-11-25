package com.inventory.exception;

import com.inventory.exception.BaseException;
import com.inventory.exception.BaseExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestControllerAdvice(basePackages = "com.inventory")
public class ExceptionControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionResponse> handleException(BaseException e) throws Exception {
        return error(e, e.getHttpErrorCode(), e.getErrorCode());
    }

    private ResponseEntity<BaseExceptionResponse> error(final Exception e, final int httpStatus, final int errorCode) {
        final String message = Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName());
        return new ResponseEntity<>(new BaseExceptionResponse(errorCode, message), HttpStatus.valueOf(httpStatus));
    }
}
