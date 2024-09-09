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
    CATEGORY_EXISTED(1008, "Category already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_NOTFOUND(1009, "Category not found", HttpStatus.NOT_FOUND),
    CHANNEL_NOT_FOUND(1010, "Channel not found", HttpStatus.NOT_FOUND),
    VIDEO_NOT_FOUND(1011, "Video not found", HttpStatus.NOT_FOUND),
    CHANNEL_SUB_CHANNEL_NOT_FOUND(1012, "Channel sub channel not found", HttpStatus.NOT_FOUND),
    HISTORY_LIKE_VIDEO_NOT_FOUND(1013, "History like video not found", HttpStatus.NOT_FOUND),
    HISTORY_WATCH_VIDEO_NOT_FOUND(1014, "History watch video not found", HttpStatus.NOT_FOUND),
    CHANNEL_NAME_UNIQUE_EXISTED(1015, "Channel name unique already exists", HttpStatus.BAD_REQUEST),
    HISTORY_NOTIFICATION_VIDEO_NOT_FOUND(1016, "History notification video not found", HttpStatus.NOT_FOUND),
    COMMENT_VIDEO_NOT_FOUND(1017, "Comment video not found", HttpStatus.NOT_FOUND),
    COMMENT_COMMENT_NOT_FOUND(1018, "Comment comment not found", HttpStatus.NOT_FOUND),
    HISTORY_NOTIFICATION_COMMENT_VIDEO_NOT_FOUND(1019, "History notification comment video not found", HttpStatus.NOT_FOUND),
    HISTORY_NOTIFICATION_COMMENT_COMMENT_NOT_FOUND(1020, "History notification comment comment not found", HttpStatus.NOT_FOUND),
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
