package com.playtech;

import com.playtech.model.Car;
import com.playtech.model.ParkingTicket;

import java.util.List;

public sealed interface ParkingManager permits ParkingManagerWithDifferentNeighboursByManufacturer {

    List<ParkingTicket> park(List<Car> cars);

}