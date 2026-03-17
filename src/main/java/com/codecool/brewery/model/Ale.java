package com.codecool.brewery.model;
import java.time.LocalDate;
public final class Ale extends Beer {
    private static final int FERMENTATION_DAYS = 14;
    private static final double MAX_STORAGE_TEMPERATURE = 25.0;
    private static final double MIN_STORAGE_TEMPERATURE = 5.0;
    public Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }
    @Override
    public int getFermentationDays() {
        return FERMENTATION_DAYS;
    }
    @Override
    public boolean canBeStoredAt(double temperatureCelsius) {
        return temperatureCelsius >= MIN_STORAGE_TEMPERATURE && temperatureCelsius <= MAX_STORAGE_TEMPERATURE;
    }
}