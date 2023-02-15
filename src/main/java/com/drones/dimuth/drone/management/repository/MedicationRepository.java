package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.Medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
}

