package com.example.SmartHouseAPI.exception;

public class EntityNotFoundException extends AppException {
    public EntityNotFoundException(EntityType entityType) {
        super(entityType);
    }
}
