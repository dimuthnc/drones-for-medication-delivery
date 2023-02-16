package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.dao.BatteryLevel;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.service.DroneService;
import java.util.List;
import javax.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for drone related operations.
 */
@RestController
@RequestMapping("api/v1/drone")
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

    @GetMapping("status")
    public List<Drone> getAvailableDrones(@RequestParam(value = "filter") String filter)
            throws DroneManagementServiceException {
        if (filter.equalsIgnoreCase("available")) {
            return droneService.getAllAvailableDrones();
        } else {
            throw new DroneManagementServiceException("Invalid filter parameter. Only 'available' is supported.");
        }

    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public Drone addDrone(@RequestBody Drone drone) throws DroneManagementServiceException {
        return droneService.addDrone(drone);
    }

    @GetMapping("status/{droneSerialNumber}")
    public BatteryLevel getDroneBatteryLevel(@PathVariable String droneSerialNumber, @RequestParam(value = "filter") String filter)
            throws DroneManagementServiceException, NotSupportedException {
        if (!filter.equalsIgnoreCase("battery")) {
            throw new DroneManagementServiceException("Invalid filter parameter. Only 'battery' is supported.");
        }
        return droneService.getDroneBatteryLevel(droneSerialNumber);
    }

}
