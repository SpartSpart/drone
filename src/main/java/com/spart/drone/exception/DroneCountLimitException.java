package com.spart.drone.exception;

public class DroneCountLimitException extends RuntimeException {
    public DroneCountLimitException(String message) {
        super(message);
    }
}
