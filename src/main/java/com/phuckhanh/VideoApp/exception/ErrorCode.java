package com.phuckhanh.VideoApp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1002, "Unauthorized", HttpStatus.FORBIDDEN),
    ACCOUNT_EXISTED(1003, "Account already exists", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND(1004, "Account not found", HttpStatus.NOT_FOUND),
    CHANNEL_EXISTED(1005, "Channel already exists", HttpStatus.BAD_REQUEST),
    INVALID_VERIFY_EMAIL(1006, "Invalid Verify Email", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1007, "Invalid password", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
