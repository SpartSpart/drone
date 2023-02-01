package com.spart.drone.service.validator;

public enum ValidationMessages {

    LIMIT_LOAD_WEIGHT("Limit of weight for the drone is overfull"),
    LOW_BATTERY_LEVEL("The drone battery level is less then "),
    DRONE_IS_IN_LOADING_STATE("The drone is already in state 'LOADING'"),
    LIMIT_NUMBER_DRONE_MESSAGE("Limit of total drones is overfull, max number is "),
    NO_SUCH_ELEMENT_IN_DB("No element in Database, entity: "),
    CONSTRAINT_VIOLATION_EXCEPTION_MESSAGE("Such element already exists in database"),
    IMAGE_EXCEPTION("Error image download/upload") ;
    private final String message;

    /**
     * @param message
     */
    ValidationMessages(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
