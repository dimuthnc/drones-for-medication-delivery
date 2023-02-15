package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.dao.Medication;
import com.drones.dimuth.drone.management.dao.MedicationDelivery;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneState;
import com.drones.dimuth.drone.management.repository.DeliveryRepository;
import com.drones.dimuth.drone.management.util.DroneManagementConstants;
import com.drones.dimuth.drone.management.util.DroneManagementUtil;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to manage deliveries.
 */
@Service
public class DeliveryService {

    private static final Log log = LogFactory.getLog(DroneService.class);

    DeliveryRepository deliveryRepository;

    DroneService droneService;

    MedicationDeliveryService medicationDeliveryService;

    MedicationService medicationService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, DroneService droneService,
                           MedicationDeliveryService medicationDeliveryService, MedicationService medicationService) {
        this.deliveryRepository = deliveryRepository;
        this.droneService = droneService;
        this.medicationDeliveryService = medicationDeliveryService;
        this.medicationService = medicationService;
    }

    public Delivery getDeliveryByDrone(String id) throws DroneManagementServiceException {

        Optional<Drone> droneOptional = droneService.findDroneBySerialNumber(id);
        if (droneOptional.isPresent() && DroneManagementUtil.isDroneLoaded(droneOptional.get())) {
            Drone drone = droneOptional.get();
            return deliveryRepository.findByDrone(drone);
        } else {
            if (!droneOptional.isPresent()) {
                log.error("Drone for the given serial number " + id + " is not loaded with medications");
                throw new DroneManagementServiceException(
                        "Drone for the given serial number " + id + " is not loaded with medications");
            } else {
                log.error("Serial number " + id + " is not a valid drone serial number with a medications load");
                throw new DroneManagementServiceException(
                        "Serial number " + id + " is not a valid drone serial number with a medications load");
            }
        }
    }

    @Transactional
    public void addDelivery(Delivery delivery) throws DroneManagementServiceException {
        Optional<Drone> drone = droneService.findDroneBySerialNumber(delivery.getDrone().getSerialNumber());
        if (drone.isPresent() && drone.get().getBatteryLevel() > DroneManagementConstants.MIN_BATTERY_FOR_LOADING) {
            Delivery savedDelivery = deliveryRepository.save(delivery);
            List<MedicationDelivery> medicationDeliveries = delivery.getMedicationDeliveries();
            double droneWeightLimit = drone.get().getWeightLimit();
            double weightSum = 0;
            for (MedicationDelivery medicationDelivery : medicationDeliveries) {
                medicationDelivery.setDelivery(savedDelivery);
                Optional<Medication> medication =
                        medicationService.getMedicationByCode(medicationDelivery.getMedication().getCode());
                if (medication.isPresent()) {
                    weightSum += medication.get().getWeight();
                    if (weightSum < droneWeightLimit) {
                        medicationDelivery.setMedication(medication.get());
                        saveMedicationDelivery(medicationDelivery);
                    } else {
                        log.error("Drone " + drone.get().getSerialNumber() + " can carry only " + droneWeightLimit +
                                " grams" + " but the request load weight is " + weightSum);
                        throw new DroneManagementServiceException(
                                "Drone " + drone.get().getSerialNumber() + " can carry only " + droneWeightLimit +
                                        " grams" + " but the request load weight more");
                    }
                } else {
                    log.error("Medication " + medicationDelivery.getMedication().getCode() + " not found");
                    throw new DroneManagementServiceException(
                            "Medication " + medicationDelivery.getMedication().getCode() + " not found");
                }
            }
            droneService.updateDroneStatus(delivery.getDrone(), DroneState.LOADED);
        } else {
            if (drone.isEmpty()) {
                throw new DroneManagementServiceException(
                        "Drone with serial number " + delivery.getDrone().getSerialNumber() + " not found");
            } else {
                log.error("Drone " + drone.get().getSerialNumber() + " has less than " +
                        DroneManagementConstants.MIN_BATTERY_FOR_LOADING + " battery");
                throw new DroneManagementServiceException("Drone " + drone.get().getSerialNumber() + " has less than " +
                        DroneManagementConstants.MIN_BATTERY_FOR_LOADING + " battery");
            }
        }
    }

    private void saveMedicationDelivery(MedicationDelivery medicationDelivery) {
        medicationDeliveryService.addMedicationDelivery(medicationDelivery);

    }
}
