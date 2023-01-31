package com.spart.drone.service.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Response {

    private StatusWithErrors status = StatusWithErrors.build();

    public static Response build(String... descriptions) {
        return build(Arrays.asList(descriptions));
    }

    public static Response build(List<String> errors) {
        Response response = new Response();
        response.getStatus().setErrors(errors);
        return response;
    }

    public void addError(String description) {
        List<String> errors = this.status.getErrors();
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(description);
        this.status.setErrors(errors);
    }

    public StatusWithErrors getStatus() {
        return status;
    }
}