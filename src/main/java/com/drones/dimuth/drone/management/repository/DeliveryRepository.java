package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.dao.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for Delivery.
 */
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByDrone(Drone d);
}
