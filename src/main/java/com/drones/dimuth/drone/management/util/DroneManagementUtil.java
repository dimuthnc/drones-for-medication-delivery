package com.drones.dimuth.drone.management.util;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.model.DroneState;
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
        return drone.getSerialNumber() != null && drone.getModel() != null && drone.getWeightLimit() > 0 &&
                drone.getSerialNumber().length() <= 100 && drone.getWeightLimit() <= 500 &&
                drone.getBatteryLevel() >= 0 && drone.getBatteryLevel() <= 100;
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
}
