package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a brewery that manages beer production and staff.
 * Many methods are incomplete and need to be implemented.
 */
public class Brewery {
    String name;
    List<Brewer> brewers;

    public Brewery(String name) {}

    void addBrewer(Brewer brewer) {
        // TODO: Implement proper null checking and validation
        // TODO: add brewer to brewers list
    }

    void addBeerToStock(Beer beer) {
        // TODO: Implement proper null checking and validation
        // TODO: add beer to stock
    }

    /**
     * Retrieves a specific quantity of beer from stock.
     * TODO: Implement this method with proper error handling
     * - What happens if the beer doesn't exist?
     * - What happens if there's not enough quantity?
     * - Should this modify the stock?
     */
    void retrieveBeerFromStock(String beerName, int quantity) {
        // TODO: Implement
    }

    /**
     * Calculates the total production capacity across specific days.
     * Each day is independent - beer can be produced in a single day.
     * TODO: Implement this method
     * - Find the master brewer
     * - Count available helpers on each specified day
     * - Return the sum of helper counts across all days
     */
    public int calculateProductionCapacity(List<DayOfWeek> days) {
        // TODO: Implement
        return 0;
    }

    /**
     * Finds beers that are past their prime (older than 180 days).
     * TODO: Implement this method using Stream API
     * - Filter beers by age
     * - Return collection of beer names
     */
    public List<String> findMaturedBeers() {
        // TODO: Implement
        return null;
    }
}
