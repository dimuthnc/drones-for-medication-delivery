package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{droneSerialNumber}")
    public Delivery getDeliveryByDroneId(@PathVariable String droneSerialNumber) {
        return deliveryService.getDeliveryByDroneId(droneSerialNumber);
    }
}
