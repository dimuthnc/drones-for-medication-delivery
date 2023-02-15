package com.drones.dimuth.drone.management.dao;

/**
 * Entity class for battery level.
 */
public class BatteryLevel {
    double batteryLevel;

    public BatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
