package com.playtech;

import com.playtech.model.Car;
import com.playtech.model.Parking;
import com.playtech.model.ParkingTicket;

import java.util.List;
import java.util.Random;

/**
 * <b>Vertical</b> and <b>horizontal</b> parked cars are considered as neighbours.
 * <br><b>Empty</b> slots considered as valid neighbours.
 */
public class ParkingManagerWithDifferentNeighboursByManufacturer implements ParkingManager {

    private final Random random;
    private final Parking parking;

    public ParkingManagerWithDifferentNeighboursByManufacturer(Random random, Parking parking) {
        this.random = random;
        this.parking = parking;
    }

    @Override
    public List<ParkingTicket> park(List<Car> cars) {
        throw new UnsupportedOperationException();
    }
}