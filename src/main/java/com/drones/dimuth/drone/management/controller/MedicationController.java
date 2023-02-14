package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.dao.MedicationListResponse;
import com.drones.dimuth.drone.management.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/medications")
public class MedicationController {
    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    public MedicationListResponse getAllMedications() {
        return medicationService.getAllMedications();
    }

}
