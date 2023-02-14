package com.drones.dimuth.drone.management;

import com.drones.dimuth.drone.management.service.DroneService;
import com.drones.dimuth.drone.management.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesManagementServiceApplication {

    private static MedicationService medicationService;
    private static DroneService droneService;

    @Autowired
    public DronesManagementServiceApplication(MedicationService medicationService, DroneService droneService) {
        DronesManagementServiceApplication.medicationService = medicationService;
        DronesManagementServiceApplication.droneService = droneService;
    }

    static MedicationService getMedicationService() {
        return medicationService;
    }

    static DroneService getDroneService() {
        return droneService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DronesManagementServiceApplication.class, args);
        SampleDroneManagementDataProvider.addSampleMedications();
        SampleDroneManagementDataProvider.addSampleDrones();

    }
}
