package com.drones.dimuth.drone.management.service.drone;

import com.drones.dimuth.drone.management.service.model.DroneState;
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
        if(!isValidDroneAddRequest(drone)) {
            throw new IllegalStateException("Invalid request parameter");
        }
        else {
            drone.setState(DroneState.IDLE);
            droneRepository.save(drone);
        }
    }

    private static boolean isValidDroneAddRequest(Drone drone) {
        return drone.getSerialNumber() != null
                && drone.getModel() != null
                && drone.getWeightLimit() > 0
                && drone.getSerialNumber().length() <= 100
                && drone.getWeightLimit() <= 500
                && drone.getState() == null
                && drone.getBatteryLevel() >= 0
                && drone.getBatteryLevel() <= 100;
    }
}
