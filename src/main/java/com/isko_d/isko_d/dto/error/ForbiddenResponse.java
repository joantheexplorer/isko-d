package com.isko_d.isko_d.dto.error;

import jakarta.servlet.http.HttpServletRequest;

public class ForbiddenResponse extends ExceptionResponse {
    public ForbiddenResponse(String message, HttpServletRequest request) {
        super(403, "Forbidden", message, request);
    }
}
