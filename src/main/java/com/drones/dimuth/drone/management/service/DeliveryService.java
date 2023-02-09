package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }
    public Delivery getDeliveryByDroneId(String id) {
        return deliveryRepository.findDeliveryById(id);
    }
}
