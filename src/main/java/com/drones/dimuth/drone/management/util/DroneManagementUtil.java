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

    /**
     * Utility method to determine drone state change is valid.
     *
     * @param currentState Current status of the drone.
     * @param state        proposed state.
     * @return boolean value indicating the validity of the state change.
     */
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

    /**
     * Method to validate drone add request.
     *
     * @param drone drone object proposed for adding to the system.
     * @return boolean value indicating the validity of request.
     */
    public static boolean isValidDroneAddRequest(Drone drone) {
        log.debug("Validating drone add request of drone " + drone);
        return drone.getSerialNumber() != null && drone.getModel() != null &&
                drone.getWeightLimit() > DroneManagementConstants.MIN_DRONE_WEIGHT_LIMIT &&
                drone.getWeightLimit() <= DroneManagementConstants.MAX_DRONE_WEIGHT_LIMIT &&
                drone.getSerialNumber().length() <= DroneManagementConstants.MAX_DRONE_SERIAL_NUMBER_LENGTH &&
                drone.getBatteryLevel() >= DroneManagementConstants.MIN_DRONE_BATTERY_LEVEL &&
                drone.getBatteryLevel() <= DroneManagementConstants.MAX_DRONE_BATTERY_LIMIT;
    }

    /**
     * Method to check drone is loaded or not.
     *
     * @param drone Drone object.
     * @return boolean value indicating the loaded status.
     */
    public static boolean isDroneLoaded(Drone drone) {
        log.debug("Checking if drone " + drone + " is loaded");
        switch (drone.getState()) {
            case LOADED:
            case DELIVERING:
            case LOADING:
                return true;
            default:
                return false;
        }
    }

    /**
     * Method to read image resources from the resource location of project.
     *
     * @param fileName name of the file.
     * @return Image as a Byte array.
     * @throws DroneManagementServiceException When failed to read the image.
     */
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
