package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.BatteryLevel;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneState;
import com.drones.dimuth.drone.management.repository.DroneRepository;
import com.drones.dimuth.drone.management.util.DroneManagementUtil;
import java.util.List;
import java.util.Optional;
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

    public void addDrone(Drone drone) throws DroneManagementServiceException {
        if (droneRepository.findDroneBySerialNumber(drone.getSerialNumber()).isPresent()) {
            throw new DroneManagementServiceException("Drone already exists");
        }
        if (!DroneManagementUtil.isValidDroneAddRequest(drone)) {
            throw new DroneManagementServiceException("Invalid request parameter");
        } else {
            drone.setState(DroneState.IDLE);
            droneRepository.save(drone);
        }
    }

    public Optional<Drone> findDroneBySerialNumber(String serialNumber) {
        return droneRepository.findDroneBySerialNumber(serialNumber);
    }

    public void updateDroneStatus(Drone drone, DroneState state) throws DroneManagementServiceException {
        Optional<Drone> droneOptional = droneRepository.findDroneBySerialNumber(drone.getSerialNumber());
        if (droneOptional.isPresent()) {
            DroneState currentState = droneOptional.get().getState();
            if (DroneManagementUtil.isValidDroneStateChange(currentState, state)) {
                droneRepository.updateDroneStatus(drone.getSerialNumber(), state);
            } else {
                throw new DroneManagementServiceException("Invalid state change");
            }
        } else {
            throw new DroneManagementServiceException("Drone not found");
        }
    }

    public DroneState getDroneState(String serialNumber) {
        return droneRepository.findDroneBySerialNumber(serialNumber).get().getState();
    }

    public List<Drone> getAllAvailableDrones() {
        return droneRepository.findDronesByState(DroneState.IDLE);
    }

    public BatteryLevel getDroneBatteryLevel(String droneSerialNumber) throws DroneManagementServiceException {
        Optional<Drone> droneOptional = droneRepository.findDroneBySerialNumber(droneSerialNumber);
        if (droneOptional.isPresent()) {
            return new BatteryLevel(droneOptional.get().getBatteryLevel());
        } else {
            throw new DroneManagementServiceException("Invalid Drone Serial Number");
        }
    }
}
