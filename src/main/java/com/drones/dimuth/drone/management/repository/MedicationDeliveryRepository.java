package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.MedicationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for MedicationDelivery.
 */
@Repository
public interface MedicationDeliveryRepository extends JpaRepository<MedicationDelivery, Long> {
}
