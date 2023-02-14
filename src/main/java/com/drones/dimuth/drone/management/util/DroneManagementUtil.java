package com.drones.dimuth.drone.management.util;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.model.DroneState;

public class DroneManagementUtil {
    public static double roundDouble(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    public static boolean isValidDroneStateChange(DroneState currentState, DroneState state) {
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
        return drone.getSerialNumber() != null && drone.getModel() != null && drone.getWeightLimit() > 0 &&
                drone.getSerialNumber().length() <= 100 && drone.getWeightLimit() <= 500 && drone.getState() == null &&
                drone.getBatteryLevel() >= 0 && drone.getBatteryLevel() <= 100;
    }
}
