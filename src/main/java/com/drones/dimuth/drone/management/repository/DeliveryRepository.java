package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.dao.MedicationDelivery;
import com.drones.dimuth.drone.management.model.DroneState;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    Delivery findByDrone(Drone d);

}
