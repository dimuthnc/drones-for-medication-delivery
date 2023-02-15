package com.drones.dimuth.drone.management.dao;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class for DroneBatteryAuditRecord.
 */
@Entity
@Table(name = "drone_battery_audit_record")
public class DroneBatteryAuditRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String droneSerialNumber;
    LocalDateTime time;
    double batteryLevel;

    public DroneBatteryAuditRecord() {

    }

    public DroneBatteryAuditRecord(String droneSerialNumber, LocalDateTime time, double batteryLevel) {
        this.droneSerialNumber = droneSerialNumber;
        this.time = time;
        this.batteryLevel = batteryLevel;
    }

    public long getId() {
        return id;
    }

    public String getDroneSerialNumber() {
        return droneSerialNumber;
    }

    public void setDroneSerialNumber(String droneSerialNumber) {
        this.droneSerialNumber = droneSerialNumber;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
