package com.drones.dimuth.drone.management.service.drone;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    Optional<Drone> findDroneBySerialNumber(String serialNumber);
}
