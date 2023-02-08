package com.drones.dimuth.drone.management.service.medication;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    @Query("SELECT m.name, m.code, m.weight FROM Medication m ")
    List<Object[]> findAllMedication();

}
