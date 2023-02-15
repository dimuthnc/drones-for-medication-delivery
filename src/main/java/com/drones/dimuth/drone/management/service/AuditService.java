package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.dao.DroneBatteryAuditRecord;
import com.drones.dimuth.drone.management.repository.DroneBatteryAuditRepository;
import com.drones.dimuth.drone.management.repository.DroneRepository;
import com.drones.dimuth.drone.management.util.DroneManagementConstants;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service to audit drone battery levels.
 */
@Service
public class AuditService {

    private final DroneRepository droneRepository;
    private final DroneBatteryAuditRepository auditRepository;

    @Autowired
    public AuditService(DroneRepository droneRepository, DroneBatteryAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
        this.droneRepository = droneRepository;
    }

    @Scheduled(fixedRate = DroneManagementConstants.AUDIT_FREQUENCY)
    public void AuditDroneBattery() {
        List<Drone> drones = droneRepository.findAll();
        drones.forEach(drone -> auditRepository.save(new DroneBatteryAuditRecord(drone.getSerialNumber(),
                LocalDateTime.now(), drone.getBatteryLevel())));
    }
}
