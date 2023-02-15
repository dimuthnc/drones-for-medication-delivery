package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.BatteryLevel;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneState;
import com.drones.dimuth.drone.management.repository.DroneRepository;
import com.drones.dimuth.drone.management.util.DroneManagementUtil;
import java.util.List;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to manage drones.
 */
@Service
public class DroneService {
    private static final Log log = LogFactory.getLog(DroneService.class);
    private final DroneRepository droneRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    public Drone addDrone(Drone drone) throws DroneManagementServiceException {
        if (droneRepository.findDroneBySerialNumber(drone.getSerialNumber()).isPresent()) {
            log.error("Drone with serial number " + drone.getSerialNumber() + " already exists");
            throw new DroneManagementServiceException(
                    "Drone with serial number " + drone.getSerialNumber() + " already exists");
        }
        if (!DroneManagementUtil.isValidDroneAddRequest(drone)) {
            log.error("Drone validation failed");
            throw new DroneManagementServiceException("Invalid request parameter");
        } else {
            if (drone.getState() == null) {
                drone.setState(DroneState.IDLE);
            }
            return droneRepository.save(drone);
        }
    }

    public Optional<Drone> findDroneBySerialNumber(String serialNumber) {
        return droneRepository.findDroneBySerialNumber(serialNumber);
    }

    public void updateDroneStatus(Drone drone, DroneState state) throws DroneManagementServiceException {
        if (log.isDebugEnabled()) {
            log.debug("Updating drone status to " + state + " for drone " + drone.getSerialNumber());
        }
        Optional<Drone> droneOptional = droneRepository.findDroneBySerialNumber(drone.getSerialNumber());
        if (droneOptional.isPresent()) {
            DroneState currentState = droneOptional.get().getState();
            if (DroneManagementUtil.isValidDroneStateChange(currentState, state)) {
                droneRepository.updateDroneStatus(drone.getSerialNumber(), state);
            } else {
                log.error("Invalid state change from " + currentState + " to " + state);
                throw new DroneManagementServiceException(
                        "Drone " + drone.getSerialNumber() + "is not in a loadable state");
            }
        } else {
            log.error("Drone with serial number " + drone.getSerialNumber() + "not found");
            throw new DroneManagementServiceException(
                    "Drone with serial number " + drone.getSerialNumber() + "not found");
        }
    }

    public List<Drone> getAllAvailableDrones() {
        return droneRepository.findDronesByState(DroneState.IDLE);
    }

    public BatteryLevel getDroneBatteryLevel(String droneSerialNumber) throws DroneManagementServiceException {
        Optional<Drone> droneOptional = droneRepository.findDroneBySerialNumber(droneSerialNumber);
        if (droneOptional.isPresent()) {
            return new BatteryLevel(droneOptional.get().getBatteryLevel());
        } else {
            log.error("Drone with serial number " + droneSerialNumber + " not found");
            throw new DroneManagementServiceException("Drone with serial number " + droneSerialNumber + " not found");
        }
    }
}
