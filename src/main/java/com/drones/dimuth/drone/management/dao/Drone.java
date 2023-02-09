package com.drones.dimuth.drone.management.dao;

import com.drones.dimuth.drone.management.model.DroneModel;
import com.drones.dimuth.drone.management.model.DroneState;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "drone")
public class Drone {
    @Id
    private String serialNumber;
    private DroneModel model;
    private double weightLimit;
    private double batteryLevel;
    private DroneState state;

    @OneToMany(mappedBy = "droneSerialNumber")
    private List<Delivery> deliveries;

    public Drone() {
    }

    public Drone(String serialNumber, DroneModel model, double weightLimit, double batteryLevel, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryLevel = batteryLevel;
        this.state = state;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public DroneState getState() {
        return state;
    }

    public void setState(DroneState state) {
        this.state = state;
    }
}
