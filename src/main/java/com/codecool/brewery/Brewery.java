package com.codecool.brewery;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a brewery that manages beer production and staff.
 * Many methods are incomplete and need to be implemented.
 */
public class Brewery {
    private String name;
    private List<Beer> stock;
    private List<Brewer> brewers;

    public Brewery(String name) {
        this.name = name;
        this.stock = new ArrayList<>();
        this.brewers = new ArrayList<>();
    }

    public void addBrewer(Brewer brewer) {
        // TODO: Implement proper null checking and validation
        brewers.add(brewer);
    }

    public void addBeerToStock(Beer beer) {
        // TODO: Implement proper null checking and validation
        stock.add(beer);
    }

    /**
     * Retrieves a specific quantity of beer from stock.
     * TODO: Implement this method with proper error handling
     * - What happens if the beer doesn't exist?
     * - What happens if there's not enough quantity?
     * - Should this modify the stock?
     */
    public List<Beer> retrieveBeerFromStock(String beerName, int quantity) {
        // TODO: Implement
        return null;
    }

    /**
     * Calculates the total production capacity across specific days.
     * Each day is independent - beer can be produced in a single day.
     * TODO: Implement this method
     * - Find the master brewer
     * - Count available helpers on each specified day
     * - Return the sum of helper counts across all days
     */
    public int calculateProductionCapacity(List<String> days) {
        // TODO: Implement
        return 0;
    }

    /**
     * Finds beers that are past their prime (older than 180 days).
     * TODO: Implement this method using Stream API
     * - Filter beers by age
     * - Return collection of beer names
     */
    public List<String> findExpiredBeers() {
        // TODO: Implement
        return null;
    }

    /**
     * Finds the optimal days for production within a given range.
     * TODO: Implement this algorithm
     * - Consider helper availability
     * - Use Stream API for calculations
     * - Return days with maximum production capacity
     */
    public List<String> findOptimalProductionDays(String startDay, String endDay) {
        // TODO: Implement
        return null;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Beer> getStock() {
        return new ArrayList<>(stock); // Defensive copy
    }

    public List<Brewer> getBrewers() {
        return new ArrayList<>(brewers); // Defensive copy
    }
}
