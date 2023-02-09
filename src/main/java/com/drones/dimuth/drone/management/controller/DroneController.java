package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.service.DroneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/drones")
public class DroneController {

    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public List<Drone> geAllDrones() {
        return droneService.getAllDrones();
    }

    @PostMapping
    public void addDrone(@RequestBody Drone drone) {
        droneService.addDrone(drone);
    }


}
