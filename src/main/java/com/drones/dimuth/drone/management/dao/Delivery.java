package com.drones.dimuth.drone.management.dao;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class for Delivery.
 */
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @JsonManagedReference
    @OneToMany(mappedBy = "delivery")
    private List<MedicationDelivery> medicationDeliveries;

    public Delivery() {
    }

    public Delivery(Drone drone, List<MedicationDelivery> medicationDeliveries) {
        this.drone = drone;
        this.medicationDeliveries = medicationDeliveries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<MedicationDelivery> getMedicationDeliveries() {
        return medicationDeliveries;
    }

    public void setMedicationDeliveries(List<MedicationDelivery> medicationDeliveries) {
        this.medicationDeliveries = medicationDeliveries;
    }


    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
}
