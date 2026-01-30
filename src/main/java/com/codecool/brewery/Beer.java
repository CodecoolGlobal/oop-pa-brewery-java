package com.codecool.brewery;

import java.time.LocalDate;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
public class Beer {
    String name;
    double alcoholContent;
    LocalDate productionDate;

    public Beer(String name, double alcoholContent, LocalDate productionDate) {}

    // TODO: Add method to calculate days until beer is ready for sale
    // TODO: Consider how to handle different fermentation times for different beer types
    public int getDaysUntilReady() {
        return 0;
    }

    // TODO: Add method to get storage temperature requirement
    // TODO: Consider canBeStoredAt method may accept parameters for temperature checks
    public boolean canBeStoredAt() {
        return false;
    }

    // TODO: Add method to get fermentation days
    public int getFermentationDays() {
        return 0;
    }
}
