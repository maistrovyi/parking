package com.playtech.model;

public record Parking(int floors, int boxesPerFloor) {

    public Parking {
        if (floors <= 0) {
            throw new IllegalArgumentException();
        }

        if (boxesPerFloor <= 0) {
            throw new IllegalArgumentException();
        }
    }

    public int size() {
        return floors * boxesPerFloor;
    }
}