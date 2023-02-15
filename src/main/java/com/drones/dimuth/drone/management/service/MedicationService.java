package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.Medication;
import com.drones.dimuth.drone.management.repository.MedicationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {

    MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public Optional<Medication> getMedicationByCode(String code) {
        return medicationRepository.findById(code);
    }


}
