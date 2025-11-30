package com.isko_d.isko_d.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> entity, Integer id) {
        super(String.format("%s with id %d not found.", entity.getSimpleName(), id));
    }

    public NotFoundException(String message) {
        super(message);
    }
}
