package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.model.DroneState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for Drone.
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    Optional<Drone> findDroneBySerialNumber(String serialNumber);

    @Modifying
    @Query("UPDATE Drone d SET d.state = ?2 WHERE d.serialNumber = ?1")
    void updateDroneStatus(String serialNumber, DroneState state);

    List<Drone> findDronesByState(DroneState state);
}
