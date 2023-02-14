package com.drones.dimuth.drone.management.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MedicationDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery deliveryId) {
        this.delivery = deliveryId;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication code) {
        this.medication = code;
    }
}
