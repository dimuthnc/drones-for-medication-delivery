package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.MedicationDelivery;
import com.drones.dimuth.drone.management.repository.MedicationDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationDeliveryService {
    MedicationDeliveryRepository medicationDeliveryRepository;

    @Autowired
    public MedicationDeliveryService(MedicationDeliveryRepository medicationDeliveryRepository) {
        this.medicationDeliveryRepository = medicationDeliveryRepository;
    }

    public void addMedicationDelivery(MedicationDelivery medicationDelivery) {
        this.medicationDeliveryRepository.save(medicationDelivery);
    }
}
