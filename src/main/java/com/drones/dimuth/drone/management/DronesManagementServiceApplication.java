package com.drones.dimuth.drone.management;

import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.dao.Medication;
import com.drones.dimuth.drone.management.service.MedicationService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

	private static MedicationService medicationService;
	private static final Log log = LogFactory.getLog(DronesManagementServiceApplication.class);
	@Autowired
	public DronesManagementServiceApplication(MedicationService medicationService) {
		DronesManagementServiceApplication.medicationService = medicationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DronesManagementServiceApplication.class, args);
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

		List<String> imageNames = Arrays.asList("1.png", "2.png", "3.jpeg", "4.jpeg");
		List<Medication> medications = new ArrayList<>();
		for (String imageName : imageNames) {
			System.out.println(imageName);
			double weight = ThreadLocalRandom.current().nextDouble(0, 500);
			byte[] image = readImageFile(imageName);
			System.out.println(image.length);
			String code = "medication_" + ThreadLocalRandom.current().nextInt(0, 1000);
			String name = "medication_" + ThreadLocalRandom.current().nextInt(0, 1000);
			Medication medication = new Medication(weight, code, image, name);
			medications.add(medication);
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

}
