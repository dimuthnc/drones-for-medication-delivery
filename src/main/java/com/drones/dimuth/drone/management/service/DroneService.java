package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.BatteryLevel;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneState;
import com.drones.dimuth.drone.management.repository.DroneRepository;
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

    public Optional<Drone> findDroneBySerialNumber(String serialNumber) {
        return droneRepository.findDroneBySerialNumber(serialNumber);
    }

    public void updateDroneStatus(Drone drone, DroneState state) {
        Optional<Drone> droneOptional =
                droneRepository.findDroneBySerialNumber(drone.getSerialNumber());
        if(droneOptional.isPresent()) {
            DroneState currentState = droneOptional.get().getState();
            if(isValidStateChange(currentState, state)) {
                droneRepository.updateDroneStatus(drone.getSerialNumber() , state);
            }
            else {
                throw new IllegalStateException("Invalid state change");
            }
            droneRepository.updateDroneStatus(drone.getSerialNumber() , state);
        }
        else {
            throw new IllegalStateException("Drone not found");
        }

    }

    private boolean isValidStateChange(DroneState currentState, DroneState state) {
        switch (currentState) {
            case IDLE:
                return state == DroneState.LOADED;
            case LOADED:
                return state == DroneState.DELIVERING;
            case DELIVERING:
                return state == DroneState.DELIVERED;
            case DELIVERED:
                return state == DroneState.IDLE;
            default:
                return false;
        }
    }

    public DroneState getDroneState(String serialNumber) {
        return droneRepository.findDroneBySerialNumber(serialNumber).get().getState();
    }

    public List<Drone> getAllAvailableDrones() {
        return droneRepository.findDronesByState(DroneState.IDLE);
    }

    public BatteryLevel getDroneBatteryLevel(String droneSerialNumber) throws DroneManagementServiceException {
        Optional<Drone> droneOptional =
                droneRepository.findDroneBySerialNumber(droneSerialNumber);
        if(droneOptional.isPresent()){
            return new BatteryLevel(droneOptional.get().getBatteryLevel());

        }
        else {
            throw new DroneManagementServiceException("Invalid Drone Serial Number");
        }
    }
}
