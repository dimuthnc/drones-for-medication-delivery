package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.DroneBatteryAuditRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for DroneBatteryAuditRecord.
 */
public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAuditRecord, Long> {

    List<DroneBatteryAuditRecord> findDroneBatteryAuditRecordByDroneSerialNumber(String droneSerialNumber);
}
