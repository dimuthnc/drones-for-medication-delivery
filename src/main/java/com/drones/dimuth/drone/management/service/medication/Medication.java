package com.drones.dimuth.drone.management.service.medication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Medication {


    private double weight;
    @Id
    private String code;
    @Column(length = 10000000)
    private byte[] image;

    private String name;

    public Medication() {

    }

    public Medication(double weight, String code, byte[] image, String name) {
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
