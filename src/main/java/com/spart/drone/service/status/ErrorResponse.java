package com.spart.drone.service.status;

import java.util.Arrays;
import java.util.List;

public class ErrorResponse {

    private StatusWithErrors status = StatusWithErrors.build();

    public static ErrorResponse build(String... descriptions) {
        return build(Arrays.asList(descriptions));
    }

    public static ErrorResponse build(List<String> errors) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.getStatus().setErrors(errors);
        return errorResponse;
    }

    public StatusWithErrors getStatus() {
        return status;
    }
}