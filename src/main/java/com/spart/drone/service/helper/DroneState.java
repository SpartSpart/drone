package com.spart.drone.service.helper;

public enum DroneState {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");

    private final String state;

    /**
     * @param state
     */
    DroneState(final String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}