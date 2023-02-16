package com.drones.dimuth.drone.management.util;

import com.drones.dimuth.drone.management.dao.Drone;
import com.drones.dimuth.drone.management.model.DroneModel;
import com.drones.dimuth.drone.management.model.DroneState;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities required for tests.
 */
public class TestUtils {

    public static List<Drone> getSampleDrones() {
        List<Drone> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Drone drone =
                    new Drone(String.valueOf(i), DroneModel.values()[i % DroneModel.values().length], i * 200, i * 20,
                            DroneState.values()[i]);
            result.add(drone);
        }
        return result;
    }
}
