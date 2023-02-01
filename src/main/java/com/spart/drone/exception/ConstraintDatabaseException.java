package com.spart.drone.exception;

import com.spart.drone.service.validator.ValidationMessages;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConstraintDatabaseException extends RuntimeException {

    public ConstraintDatabaseException() {
        super(ValidationMessages.CONSTRAINT_VIOLATION_EXCEPTION_MESSAGE.toString());
        log.error(ValidationMessages.CONSTRAINT_VIOLATION_EXCEPTION_MESSAGE.toString());
    }

}
