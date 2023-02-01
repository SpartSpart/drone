package com.spart.drone.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DroneCountLimitException extends RuntimeException {

    public DroneCountLimitException(String message) {
        super(message);
        log.error(message);
    }
}
