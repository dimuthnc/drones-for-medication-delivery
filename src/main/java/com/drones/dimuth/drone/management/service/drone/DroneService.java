package com.drones.dimuth.drone.management.service.drone;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

    private final DroneRepository droneRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    public void addDrone(Drone drone) {
        if(droneRepository.findDroneBySerialNumber(drone.getSerialNumber()).isPresent()) {
            throw new IllegalStateException("Drone already exists");
        }
        else {
            droneRepository.save(drone);
        }

    }
}
