package com.spart.drone.service.status;

import java.util.Arrays;
import java.util.List;

public class StatusWithErrors extends Status {

    private List<String> errors;

    public static StatusWithErrors build(String... errors) {
        StatusWithErrors statusWithErrors = new StatusWithErrors();
        statusWithErrors.setCode(Code.error);
        statusWithErrors.setErrors(Arrays.asList(errors));
        return statusWithErrors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        if (errors != null && !errors.isEmpty()) {
            this.errors = errors;
        }
    }
}
