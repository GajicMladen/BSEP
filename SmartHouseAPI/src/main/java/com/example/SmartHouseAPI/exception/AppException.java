package com.example.SmartHouseAPI.exception;

import static com.example.SmartHouseAPI.exception.EntityType.getEntityErrorMessage;

public class AppException extends Exception {

    private final String message;

    public AppException(EntityType entityType) {
        super();
        this.message = createExceptionMessage(entityType);
    }

    public AppException(String message) {
        super();
        this.message = message;
    }

    private String createExceptionMessage(EntityType entityType) {
        return getEntityErrorMessage(entityType);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
