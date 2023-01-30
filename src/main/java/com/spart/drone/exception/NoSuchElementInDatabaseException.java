package com.spart.drone.exception;

import java.util.function.Supplier;

public class NoSuchElementInDatabaseException extends RuntimeException {

    public NoSuchElementInDatabaseException(String entityName) {
        super("No element in Database, entity: " + entityName);
    }

}
