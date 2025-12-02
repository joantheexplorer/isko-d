package com.isko_d.isko_d.dto.error;

import jakarta.servlet.http.HttpServletRequest;

public class NotFoundResponse extends ExceptionResponse {
    public NotFoundResponse(String message, HttpServletRequest request) {
        super(404, "Not Found", message, request);
    }
}
