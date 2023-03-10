package com.drones.dimuth.drone.management;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.dao.Medication;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneModel;
import com.drones.dimuth.drone.management.util.DroneManagementConstants;
import com.drones.dimuth.drone.management.util.DroneManagementUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class to add sample data to the application.
 */
public class SampleDroneManagementDataProvider {

    private static final Log log = LogFactory.getLog(SampleDroneManagementDataProvider.class);

    /**
     * Method to add sample drones.
     */
    static void addSampleDrones() {
        log.info("Adding Sample drone data");
        List<DroneModel> models =
                Arrays.asList(DroneModel.Cruiserweight, DroneModel.Lightweight, DroneModel.Middleweight,
                        DroneModel.Heavyweight);
        //SAMPLE_DRONE_COUNT is set to 10 in DroneManagementConstants for testing purposes.
        for (int i = 0; i < DroneManagementConstants.SAMPLE_DRONE_COUNT; i++) {
            String serialNumber = String.valueOf(i);
            DroneModel model = models.get(ThreadLocalRandom.current().nextInt(0, DroneModel.values().length));
            double weightLimit = DroneManagementUtil.roundDouble(ThreadLocalRandom.current()
                    .nextDouble(DroneManagementConstants.SAMPLE_DRONE_MIN_WEIGHT,
                            DroneManagementConstants.SAMPLE_DRONE_MAX_WEIGHT));
            double batteryLevel = DroneManagementUtil.roundDouble(ThreadLocalRandom.current()
                    .nextDouble(DroneManagementConstants.SAMPLE_DRONE_MIN_BATTERY_LEVEL,
                            DroneManagementConstants.SAMPLE_DRONE_MAX_BATTERY_LEVEL));
            Drone drone = new Drone(serialNumber, model, weightLimit, batteryLevel);
            try {
                log.debug("Adding drone " + drone);
                DronesManagementServiceApplication.getDroneService().addDrone(drone);
            } catch (DroneManagementServiceException e) {
                throw new RuntimeException("Wrong initial input data provided", e);
            }
        }
    }

    /**
     * Method to add sample medications.
     */
    static void addSampleMedications() {
        log.info("Adding Sample medication data");
        List<Medication> medications = new ArrayList<>();
        try {
            medications = getSampleMedications();
        } catch (DroneManagementServiceException e) {
            log.error("Error while reading medication data", e);
        }
        medications.forEach(
                medication -> DronesManagementServiceApplication.getMedicationService().saveMedication(medication));
    }

    /**
     * Method to retrieve sample data for medication.
     *
     * @return List of medication objects.
     * @throws DroneManagementServiceException When failed to load the medication images
     */
    private static List<Medication> getSampleMedications() throws DroneManagementServiceException {

        List<String> imageNames = Arrays.asList("1.png", "2.png", "3.png", "4.jpeg");
        List<String> medicationNames = Arrays.asList("Paracetamol", "Ibuprofen", "Aspirin", "Caffeine");
        List<String> medicationCodes = Arrays.asList("1234", "1235", "1236", "1237");
        List<Medication> medications = new ArrayList<>();
        //SAMPLE_MEDICATION_COUNT is set to 4 in DroneManagementConstants for testing purposes.
        for (int i = 0; i < DroneManagementConstants.SAMPLE_MEDICATION_COUNT; i++) {
            double weight = DroneManagementUtil.roundDouble(ThreadLocalRandom.current()
                    .nextDouble(DroneManagementConstants.SAMPLE_MEDICATION_WEIGHT_MIN,
                            DroneManagementConstants.SAMPLE_MEDICATION_WEIGHT_MAX));
            byte[] image = DroneManagementUtil.readImageFromResources(imageNames.get(i));
            String code = medicationCodes.get(i);
            String name = medicationNames.get(i);
            Medication medication = new Medication(weight, code, image, name);
            log.debug("Adding medication " + medication);
            medications.add(medication);
        }
        return medications;
    }
}
