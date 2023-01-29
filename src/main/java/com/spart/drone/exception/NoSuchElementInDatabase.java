package com.spart.drone.exception;

import java.util.function.Supplier;

public class NoSuchElementInDatabase extends Throwable {

    public NoSuchElementInDatabase() {
        super("No element in Database");
    }

}
