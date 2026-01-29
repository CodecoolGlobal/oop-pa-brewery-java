package com.codecool.brewery;

import java.time.LocalDate;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
public class Beer {
    private String name;
    private double alcoholContent;
    private LocalDate productionDate;

    public Beer(String name, double alcoholContent, LocalDate productionDate) {
        this.name = name;
        this.alcoholContent = alcoholContent;
        this.productionDate = productionDate;
    }

    public String getName() {
        return name;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    // TODO: Add method to calculate days until beer is ready for sale
    // TODO: Consider how to handle different fermentation times for different beer types
    public int getDaysUntilReady() {
        return 0;
    }
}
