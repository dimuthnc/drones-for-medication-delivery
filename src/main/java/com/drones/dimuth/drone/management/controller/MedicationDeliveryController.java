package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.service.MedicationDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/medication-delivery")
public class MedicationDeliveryController {

    MedicationDeliveryService medicationDeliveryService;

    @Autowired
    public MedicationDeliveryController(MedicationDeliveryService medicationDeliveryService) {
        this.medicationDeliveryService = medicationDeliveryService;
    }

}
