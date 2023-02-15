package com.drones.dimuth.drone.management.util;

import com.drones.dimuth.drone.management.SampleDroneManagementDataProvider;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneState;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class for Drone Management.
 */
public class DroneManagementUtil {

    private static final Log log = LogFactory.getLog(DroneManagementUtil.class);

    public static double roundDouble(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    public static boolean isValidDroneStateChange(DroneState currentState, DroneState state) {
        if (log.isDebugEnabled()) {
            log.debug("Validating drone state change from " + currentState + " to " + state);
        }
        switch (currentState) {
            case IDLE:
                return state == DroneState.LOADED;
            case LOADED:
                return state == DroneState.DELIVERING;
            case DELIVERING:
                return state == DroneState.DELIVERED;
            case DELIVERED:
                return state == DroneState.IDLE;
            default:
                return false;
        }
    }

    public static boolean isValidDroneAddRequest(Drone drone) {
        log.debug("Validating drone add request of drone " + drone);
        return drone.getSerialNumber() != null && drone.getModel() != null &&
                drone.getWeightLimit() > DroneManagementConstants.MIN_DRONE_WEIGHT_LIMIT &&
                drone.getWeightLimit() <= DroneManagementConstants.MAX_DRONE_WEIGHT_LIMIT &&
                drone.getSerialNumber().length() <= DroneManagementConstants.MAX_DRONE_SERIAL_NUMBER_LENGTH &&
                drone.getBatteryLevel() >= DroneManagementConstants.MIN_DRONE_BATTERY_LEVEL &&
                drone.getBatteryLevel() <= DroneManagementConstants.MAX_DRONE_BATTERY_LIMIT;
    }

    public static boolean isDroneLoaded(Drone drone) {
        log.debug("Checking if drone " + drone + " is loaded");
        switch (drone.getState()) {
            case LOADED:
            case DELIVERING:
                return true;
            default:
                return false;
        }
    }

    public static byte[] readImageFromResources(String fileName) throws DroneManagementServiceException {
        String imageLocation = DroneManagementConstants.SAMPLE_IMAGE_DIRECTORY + fileName;
        try (InputStream inputStream = SampleDroneManagementDataProvider.class.getResourceAsStream(imageLocation)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[DroneManagementConstants.IMAGE_BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error while reading image file " + fileName + " from location " + imageLocation);
            throw new DroneManagementServiceException(
                    "Error while reading image file " + fileName + " from location " + imageLocation, e);
        }
    }
}
