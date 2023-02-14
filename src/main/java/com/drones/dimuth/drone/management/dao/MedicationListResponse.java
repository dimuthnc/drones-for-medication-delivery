package com.drones.dimuth.drone.management.dao;

import java.util.List;

public class MedicationListResponse {
    List<Object[]> medications;

    public MedicationListResponse(List<Object[]> medications) {
        this.medications = medications;
    }

    public List<Object[]> getMedications() {
        return medications;
    }

    public void setMedications(List<Object[]> medications) {
        this.medications = medications;
    }
}
