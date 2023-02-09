package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.Delivery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    @Query("SELECT d FROM Delivery d WHERE d.droneSerialNumber = ?1")
    public Delivery findDeliveryById(String id);

}
