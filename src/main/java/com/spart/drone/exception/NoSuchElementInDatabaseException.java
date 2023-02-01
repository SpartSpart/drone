package com.spart.drone.exception;

import com.spart.drone.service.validator.ValidationMessages;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoSuchElementInDatabaseException extends RuntimeException {

    public NoSuchElementInDatabaseException(String entityName) {
        super(ValidationMessages.NO_SUCH_ELEMENT_IN_DB + entityName);
        log.error(ValidationMessages.NO_SUCH_ELEMENT_IN_DB + entityName);
    }

}
