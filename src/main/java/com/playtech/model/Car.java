package com.playtech.model;

import java.util.UUID;

public record Car(UUID id, Manufacturer manufacturer, String model, EngineProperties engineProps) {

}