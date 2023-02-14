package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/delivery")
public class DeliveryController {
    DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("drone/{id}")
    public Delivery getDeliveryByDroneId(@PathVariable String id) {
        Delivery delivery = deliveryService.getDeliveryByDrone(id);
        return delivery;
    }

    @PostMapping("drone")
    public void addDelivery(@RequestBody Delivery delivery) throws DroneManagementServiceException {
        deliveryService.addDelivery(delivery);
    }
}
