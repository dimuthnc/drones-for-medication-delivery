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

    private static boolean isValidDrone(Drone drone) {
        if(drone.getSerialNumber().length() > 100) {
            return false;
        }
        if (drone.getWeightLimit() > 500) {
            return false;
        }
        if(drone.getBatteryLevel() > 100){
            return false;
        }
        return true;

    }
}
