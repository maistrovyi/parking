package com.playtech;

import static com.playtech.model.Manufacturer.AUDI;
import static com.playtech.model.Manufacturer.DODGE;
import static com.playtech.model.Manufacturer.MAN;
import static com.playtech.model.Manufacturer.MAZDA;
import static com.playtech.model.Manufacturer.TOYOTA;
import static com.playtech.model.Manufacturer.VOLKSWAGEN;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.playtech.model.Car;
import com.playtech.model.EngineProperties;
import com.playtech.model.Manufacturer;
import com.playtech.model.Parking;
import com.playtech.model.ParkingSlot;
import com.playtech.model.ParkingTicket;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// do we have enough tests to ensure correctness of ParkingManagerWithDifferentNeighboursByManufacturerTest?
class ParkingManagerWithDifferentNeighboursByManufacturerTest {

    @RepeatedTest(value = 1_000)
    void park() {
        var parking = new Parking(3, 3);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), parking);

        List<ParkingTicket> parkingTickets = parkingManager.park(List.of(
                car(TOYOTA, "Camry", new EngineProperties(150, 180)),
                car(AUDI, "SQ5", new EngineProperties(410, 620)),
                car(AUDI, "A4 Allroad", new EngineProperties(252, 300)),
                car(MAZDA, "CX3", new EngineProperties(140, 165)),
                car(MAN, "TGX", new EngineProperties(360, 1_800)),
                car(VOLKSWAGEN, "Passat CC (Stage1)", new EngineProperties(250, 320)),
                car(AUDI, "RS6", new EngineProperties(510, 720)),
                car(AUDI, "e-tron", new EngineProperties(390, 600)),
                car(AUDI, "Q8", new EngineProperties(350, 425))
        ));

        verifyNeighborCarsHaveDifferentManufacturers(parkingTickets);
    }

    @Test
    void park_emptyCars() {
        var parking = new Parking(3, 3);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), parking);

        assertTrue(parkingManager.park(List.of()).isEmpty());
    }

    @Test
    void park_singleCar() {
        var parking = new Parking(1, 5);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), parking);

        verifyNeighborCarsHaveDifferentManufacturers(parkingManager.park(List.of(car(AUDI, "SQ7", new EngineProperties(560, 700)))));
    }

    @Test
    void park_twoCarsWithSameManufacturer() {
        var parking = new Parking(3, 3);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), parking);

        List<Car> cars = List.of(
                car(TOYOTA, "Corolla", new EngineProperties(128, 140)),
                car(TOYOTA, "Avensis", new EngineProperties(140, 180))
        );

        verifyNeighborCarsHaveDifferentManufacturers(parkingManager.park(cars));
    }

    @Test
    void park_fiveWithSameManufacturer() {
        var parking = new Parking(3, 3);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), parking);

        List<Car> cars = List.of(
                car(DODGE, "Challenger", new EngineProperties(280, 400)),
                car(DODGE, "Charger", new EngineProperties(290, 390)),
                car(DODGE, "Viper", new EngineProperties(305, 390)),
                car(DODGE, "Journey GT", new EngineProperties(190, 240)),
                car(DODGE, "RAM", new EngineProperties(245, 300))
        );

        verifyNeighborCarsHaveDifferentManufacturers(parkingManager.park(cars));
    }

    @Test
    void park_threeWithSameManufacturer_ButNotEnoughParkingSlots() {
        var parking = new Parking(1, 4);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), parking);

        List<Car> cars = List.of(
                car(TOYOTA, "Corolla", new EngineProperties(128, 140)),
                car(TOYOTA, "Avensis", new EngineProperties(140, 180)),
                car(TOYOTA, "Supra", new EngineProperties(300, 400))
        );

        assertThrows(Exception.class, () -> parkingManager.park(cars));
    }

    @Test
    void park_whenThereAreNoEnoughSlotsForParking() {
        var garage = new Parking(1, 1);
        var parkingManager = new ParkingManagerWithDifferentNeighboursByManufacturer(ThreadLocalRandom.current(), garage);

        List<Car> cars = List.of(
                car(MAZDA, "CX7", new EngineProperties(220, 260)),
                car(MAZDA, "RX8", new EngineProperties(280, 360))
        );

        assertThrows(Exception.class, () -> parkingManager.park(cars));
    }

    private static Car car(Manufacturer manufacturer, String model, EngineProperties engineProps) {
        return new Car(UUID.randomUUID(), manufacturer, model, engineProps);
    }

    private static void verifyNeighborCarsHaveDifferentManufacturers(List<ParkingTicket> parkingTickets) {
        Map<ParkingSlot, Manufacturer> parkedManufacturers = parkingTickets.stream()
                .collect(Collectors.toMap(ParkingTicket::parkingSlot, it -> it.car().manufacturer()));

        parkingTickets.forEach(parkingTicket -> {
            List<Manufacturer> neighbourManufacturers = findNeighboursSlots(parkingTicket.parkingSlot()).stream()
                    .map(parkedManufacturers::get)
                    .filter(Objects::nonNull)
                    .toList();

            assertFalse(neighbourManufacturers.contains(parkingTicket.car().manufacturer()));
        });
    }

    private static Set<ParkingSlot> findNeighboursSlots(ParkingSlot parkingSlot) {
        return Set.of(
                new ParkingSlot(parkingSlot.floor() - 1, parkingSlot.boxNumber()),
                new ParkingSlot(parkingSlot.floor(), parkingSlot.boxNumber() + 1),
                new ParkingSlot(parkingSlot.floor() + 1, parkingSlot.boxNumber()),
                new ParkingSlot(parkingSlot.floor(), parkingSlot.boxNumber() - 1)
        );
    }
}