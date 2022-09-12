package com.playtech.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ParkingTest {

    @Test
    void invalidInstantiationTest() {
        assertThrows(IllegalArgumentException.class, () -> new Parking(-1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Parking(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Parking(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new Parking(-1, -1));
    }

    @Test
    void size() {
        assertEquals(25, new Parking(5, 5).size());
        assertEquals(1, new Parking(1, 1).size());
    }
}