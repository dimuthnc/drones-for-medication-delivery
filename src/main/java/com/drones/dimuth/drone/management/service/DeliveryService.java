package com.drones.dimuth.drone.management.service;

import com.drones.dimuth.drone.management.dao.Delivery;
import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.dao.Medication;
import com.drones.dimuth.drone.management.dao.MedicationDelivery;
import com.drones.dimuth.drone.management.model.DroneState;
import com.drones.dimuth.drone.management.repository.DeliveryRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    DeliveryRepository deliveryRepository;

    DroneService droneService;

    MedicationDeliveryService medicationDeliveryService;

    MedicationService medicationService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, DroneService droneService,
                           MedicationDeliveryService medicationDeliveryService, MedicationService medicationService){
        this.deliveryRepository = deliveryRepository;
        this.droneService = droneService;
        this.medicationDeliveryService = medicationDeliveryService;
        this.medicationService = medicationService;
    }
    public Delivery getDeliveryByDrone(String id) {

        Optional<Drone> droneOptional = droneService.findDroneBySerialNumber(id);
        if (droneOptional.isPresent() && droneOptional.get().getState() == DroneState.LOADED) {
            Drone drone = droneOptional.get();
            return deliveryRepository.findByDrone(drone);
        }
        else {
            throw new IllegalStateException("Invalid Serial Number");
        }


    }

    @Transactional
    public void addDelivery(Delivery delivery) {
        Delivery savedDelivery = deliveryRepository.save(delivery);
        List<MedicationDelivery> medicationDeliveries = delivery.getMedicationDeliveries();
        for (MedicationDelivery medicationDelivery : medicationDeliveries) {
            medicationDelivery.setDelivery(savedDelivery);
            Optional<Medication> medication =
                    medicationService.getMedicationByCode(medicationDelivery.getMedication().getCode());
            if (medication.isPresent()) {
                medicationDelivery.setMedication(medication.get());
                saveMedicationDelivery(medicationDelivery);
            }
            else {
                throw new IllegalStateException("Medication not found");
            }
        }
        droneService.updateDroneStatus(delivery.getDrone(), DroneState.LOADED);
    }

    private void saveMedicationDelivery(MedicationDelivery medicationDelivery) {
        medicationDeliveryService.addMedicationDelivery(medicationDelivery);

    }


}
