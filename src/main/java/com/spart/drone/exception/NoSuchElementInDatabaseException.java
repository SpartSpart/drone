package com.spart.drone.exception;

import com.spart.drone.service.validator.ValidationMessages;

public class NoSuchElementInDatabaseException extends RuntimeException {

    public NoSuchElementInDatabaseException(String entityName) {
        super(ValidationMessages.NO_SUCH_ELEMENT_IN_DB + entityName);
    }

}
