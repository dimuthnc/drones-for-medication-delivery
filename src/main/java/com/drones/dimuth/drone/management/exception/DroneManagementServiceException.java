package com.drones.dimuth.drone.management.exception;

public class DroneManagementServiceException extends Exception {

    public DroneManagementServiceException(String message) {
        super(message);
    }

    public DroneManagementServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DroneManagementServiceException(Throwable cause) {
        super(cause);
    }
}
