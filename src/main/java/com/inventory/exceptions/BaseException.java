package com.inventory.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.inventory.utils.ErrorCodes;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonSerialize(using = ExceptionSerializer.class)
public class BaseException extends Exception {

    private int errorCode = ErrorCodes.GENERIC_ERROR_CODE;
    private int httpErrorCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public BaseException(String msg, Throwable t) {
        super(msg, t);
    }

    public BaseException(Integer customErrorCode, String msg) {
        super(msg);
        this.errorCode = customErrorCode;
    }

    public BaseException(Integer customErrorCode, int httpErrorCode, String msg) {
        super(msg);
        this.errorCode = customErrorCode;
        this.httpErrorCode = httpErrorCode;
    }

    public BaseException(Integer customErrorCode, String msg, Throwable t) {
        super(msg,t);
        this.errorCode = customErrorCode;
    }

    public BaseException(Integer customErrorCode, int httpErrorCode, String msg, Throwable t) {
        super(msg,t);
        this.errorCode = customErrorCode;
        this.httpErrorCode = httpErrorCode;
    }
}
