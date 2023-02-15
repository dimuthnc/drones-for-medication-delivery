package com.drones.dimuth.drone.management.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler controller for DroneManagementServiceException.
 */
@ControllerAdvice
public class DroneManagementServiceExceptionHandlerController {

    @ExceptionHandler(DroneManagementServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDroneManagementServiceException(DroneManagementServiceException e) {

    }


}
