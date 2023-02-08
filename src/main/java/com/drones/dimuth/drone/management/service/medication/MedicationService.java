package com.drones.dimuth.drone.management.service.medication;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationService  {

    MedicationRepository medicationRepository;
    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Medication saveMedication(Medication medication) {
        System.out.println(medication.getCode());
        return medicationRepository.save(medication);
    }


    public List<Object[]> getAllMedications() {
        return medicationRepository.findAllMedication();
    }
}