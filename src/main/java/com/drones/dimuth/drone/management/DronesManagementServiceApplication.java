package com.drones.dimuth.drone.management;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.dao.Medication;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneModel;
import com.drones.dimuth.drone.management.model.DroneState;
import com.drones.dimuth.drone.management.service.DroneService;
import com.drones.dimuth.drone.management.service.MedicationService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DronesManagementServiceApplication {

    private static final Log log = LogFactory.getLog(DronesManagementServiceApplication.class);
    private static MedicationService medicationService;
    private static DroneService droneService;

    @Autowired
    public DronesManagementServiceApplication(MedicationService medicationService, DroneService droneService) {
        DronesManagementServiceApplication.medicationService = medicationService;
        DronesManagementServiceApplication.droneService = droneService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DronesManagementServiceApplication.class, args);
        addSampleMedications();
        addSampleDrones();

    }

    private static void addSampleDrones() {
        List<DroneModel> models = Arrays.asList(DroneModel.Cruiserweight, DroneModel.Lightweight,
                DroneModel.Middleweight, DroneModel.Heavyweight);

        for (int i = 0; i < 10; i++) {
            String serialNumber = String.valueOf(i);
            DroneModel model = models.get(ThreadLocalRandom.current().nextInt(0, 4));
            double weightLimit = roundDouble(ThreadLocalRandom.current().nextDouble(0.0, 500.0));
            double batteryLevel = roundDouble(ThreadLocalRandom.current().nextDouble(0.0, 100.0));
            DroneState state = null;
            Drone drone = new Drone(serialNumber, model, weightLimit, batteryLevel, state);
			droneService.addDrone(drone);
		}
    }

    private static void addSampleMedications() {
        List<Medication> medications = new ArrayList<>();
        try {
            medications = readMedication();
        } catch (DroneManagementServiceException e) {
            log.error("Error while reading medication data", e);
        }
        for (Medication medication : medications) {
            medicationService.saveMedication(medication);
        }
    }

    private static List<Medication> readMedication() throws DroneManagementServiceException {

        List<String> imageNames = Arrays.asList("1.png", "2.png", "3.png", "4.jpeg");
        List<String> medicationNames = Arrays.asList("Paracetamol", "Ibuprofen", "Aspirin", "Caffeine");
        List<String> medicationCodes = Arrays.asList("1234", "1235", "1236", "1237");
        List<Medication> medications = new ArrayList<>();
        for (int i = 0 ; i < 4; i++) {
            double weight = roundDouble(ThreadLocalRandom.current().nextDouble(30, 100));
            byte[] image = readImageFile(imageNames.get(i));
            String code = medicationCodes.get(i);
            String name = medicationNames.get(i);
            Medication medication = new Medication(weight, code, image, name);
            medications.add(medication);
        }
        for (String imageName : imageNames) {

        }
        return medications;
    }

    public static byte[] readImageFile(String fileName) throws DroneManagementServiceException {
        try (InputStream inputStream = DronesManagementServiceApplication.class
                .getResourceAsStream("/medication-images/" + fileName)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new DroneManagementServiceException("Error while reading image file", e);
        }
    }

    private static double roundDouble(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

}
