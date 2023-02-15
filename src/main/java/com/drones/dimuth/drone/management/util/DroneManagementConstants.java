package com.drones.dimuth.drone.management.util;

/**
 * Constants used in the application.
 */
public class DroneManagementConstants {
    public static final double MIN_BATTERY_FOR_LOADING = 25.0;
    public static final int IMAGE_COLUMN_SIZE = 10000000;
    public static final long AUDIT_FREQUENCY = 600000; //10 minutes
    public static final int IMAGE_BUFFER_SIZE = 1024;
    public static final int MAX_DRONE_SERIAL_NUMBER_LENGTH = 100;
    public static final double MAX_DRONE_WEIGHT_LIMIT = 500;
    public static final double MIN_DRONE_BATTERY_LEVEL = 0;
    public static final double MAX_DRONE_BATTERY_LIMIT = 100;
    public static final double MIN_DRONE_WEIGHT_LIMIT = 0;

    //Constants related to sample data
    public static final int SAMPLE_DRONE_COUNT = 10;
    public static final double SAMPLE_DRONE_MAX_WEIGHT = 500.0;
    public static final double SAMPLE_DRONE_MIN_WEIGHT = 100.0;
    public static final double SAMPLE_DRONE_MIN_BATTERY_LEVEL = 10;
    public static final double SAMPLE_DRONE_MAX_BATTERY_LEVEL = 100;
    public static final int SAMPLE_MEDICATION_COUNT = 4;
    public static final double SAMPLE_MEDICATION_WEIGHT_MIN = 30;
    public static final double SAMPLE_MEDICATION_WEIGHT_MAX = 100;
    public static final String SAMPLE_IMAGE_DIRECTORY = "/medication-images/";
}
