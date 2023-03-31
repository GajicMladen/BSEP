package com.example.SmartHouseAPI.exception;

import static com.example.SmartHouseAPI.exception.ErrorMessagesConstants.CSR_NOT_FOUND;
import static com.example.SmartHouseAPI.exception.ErrorMessagesConstants.ENTITY_NOT_FOUND;

public enum EntityType {
    CSR;

    public static String getEntityErrorMessage(EntityType entityType){
        switch (entityType){
            case CSR -> {

                return CSR_NOT_FOUND;
            }
            default -> {

                return ENTITY_NOT_FOUND;
            }
        }

    }
}
