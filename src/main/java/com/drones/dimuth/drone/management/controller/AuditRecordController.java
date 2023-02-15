package com.drones.dimuth.drone.management.controller;

import com.drones.dimuth.drone.management.dao.DroneBatteryAuditRecord;
import com.drones.dimuth.drone.management.service.AuditService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/audit/battery")
public class AuditRecordController {

    AuditService auditService;

    @Autowired
    public AuditRecordController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public List<DroneBatteryAuditRecord> getDroneBatteryAuditRecords(
            @RequestParam(value = "droneSerialNumber", required = false) String droneSerialNumber) {
        return auditService.getDroneBatteryAuditRecords(droneSerialNumber);
    }
}
