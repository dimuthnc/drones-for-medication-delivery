package com.drones.dimuth.drone.management.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception handler controller for DroneManagementServiceException.
 */
@ControllerAdvice
public class DroneManagementServiceExceptionHandlerController {

    /**
     * Handler for DroneManagementException. This method will respond to the client with 400 Bad Request
     * when the DroneManagementServiceException is thrown from code.
     *
     * @param e Exception object.
     * @return Response parameters as a map.
     */
    @ExceptionHandler(DroneManagementServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleDroneManagementServiceException(DroneManagementServiceException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        return response;
    }
}
