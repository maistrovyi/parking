package com.playtech;

import com.playtech.model.Car;
import com.playtech.model.ParkingTicket;

import java.util.List;

public interface ParkingManager {

    List<ParkingTicket> park(List<Car> cars);

}