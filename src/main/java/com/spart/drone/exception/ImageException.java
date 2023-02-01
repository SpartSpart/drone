package com.spart.drone.exception;

import com.spart.drone.service.validator.ValidationMessages;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageException extends RuntimeException {

    public ImageException() {
        super(ValidationMessages.IMAGE_EXCEPTION.toString());
        log.error(ValidationMessages.IMAGE_EXCEPTION.toString());
    }

}
