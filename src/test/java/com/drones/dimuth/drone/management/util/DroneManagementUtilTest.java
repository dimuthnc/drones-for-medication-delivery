package com.drones.dimuth.drone.management.util;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.exception.DroneManagementServiceException;
import com.drones.dimuth.drone.management.model.DroneModel;
import com.drones.dimuth.drone.management.model.DroneState;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import static org.junit.jupiter.api.Assertions.*;

class DroneManagementUtilTest {

    private static List<Drone> testDrones = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        testDrones = TestUtils.getSampleDrones();
    }

    @Test
    void roundDouble() {
        double sampleValue1 = 123.2435353535;
        double sampleValue2 = 234.6574555355;
        Assertions.assertThat(DroneManagementUtil.roundDouble(sampleValue1)).isEqualTo(123.24);
        Assertions.assertThat(DroneManagementUtil.roundDouble(sampleValue2)).isEqualTo(234.66);
    }

    @Test
    void isValidDroneStateChange() {
        int count = 0;
        for (Drone drone : testDrones) {
            if (DroneManagementUtil.isValidDroneStateChange(drone.getState(), DroneState.LOADED)) {
                count++;
            }
        }
        Assertions.assertThat(count).isEqualTo(1);

    }

    @Test
    void isValidDroneAddRequest() {
        int count = 0;
        for (Drone drone : testDrones) {
            if (DroneManagementUtil.isValidDroneAddRequest(drone)) {
                count++;
            }
        }
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void isDroneLoaded() {
        int count = 0;
        for (Drone drone : testDrones) {
            if (DroneManagementUtil.isDroneLoaded(drone)) {
                count++;
            }
        }
        Assertions.assertThat(count).isEqualTo(3);
    }

    @Test
    void readImageFromResources() throws DroneManagementServiceException {
        Assertions.assertThat(DroneManagementUtil.readImageFromResources("1.png").length).isGreaterThan(1000);
    }

    @Test
    void readInvalidImageFromResources() {
        Assertions.assertThatThrownBy(() -> DroneManagementUtil.readImageFromResources("invalid.png"))
                .isInstanceOf(NullPointerException.class);
    }
}