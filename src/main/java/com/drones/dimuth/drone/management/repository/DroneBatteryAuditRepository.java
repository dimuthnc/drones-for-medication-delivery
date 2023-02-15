package com.drones.dimuth.drone.management.repository;

import com.drones.dimuth.drone.management.dao.DroneBatteryAuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for DroneBatteryAuditRecord.
 */
public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAuditRecord, Long> {
}
