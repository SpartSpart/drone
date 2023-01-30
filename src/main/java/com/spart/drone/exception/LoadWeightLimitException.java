package com.spart.drone.exception;

public class LoadWeightLimitException extends RuntimeException {

    public LoadWeightLimitException() {
        super("Limit of weight medication is overfull for this drone");
    }

    public LoadWeightLimitException(Integer freeWeightSpace) {
        super(String.format("Available drone weight space is %s",freeWeightSpace));
    }
}
