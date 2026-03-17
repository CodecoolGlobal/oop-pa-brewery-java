package com.codecool.brewery.service;
import com.codecool.brewery.model.Beer;
import com.codecool.brewery.model.Brewer;
import com.codecool.brewery.model.HelperBrewer;
import com.codecool.brewery.model.MasterBrewer;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
public class Brewery {
    private final String name;
    private MasterBrewer masterBrewer;
    private final List<HelperBrewer> helperBrewers;
    private final Map<String, List<Beer>> stock;
    public Brewery(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Brewery name must not be null or blank.");
        }
        this.name = name;
        this.helperBrewers = new ArrayList<>();
        this.stock = new HashMap<>();
    }
    public String getName() {
        return name;
    }
    public void addBrewer(Brewer brewer) {
        if (brewer == null) {
            throw new IllegalArgumentException("Brewer must not be null.");
        }
        if (brewer instanceof MasterBrewer master) {
            this.masterBrewer = master;
        } else if (brewer instanceof HelperBrewer helper) {
            helperBrewers.add(helper);
        }
    }
    public void addBeerToStock(Beer beer) {
        if (beer == null) {
            throw new IllegalArgumentException("Beer must not be null.");
        }
        stock.computeIfAbsent(beer.getName(), k -> new ArrayList<>()).add(beer);
    }
    public List<Beer> retrieveBeerFromStock(String beerName, int quantity) {
        if (beerName == null || beerName.isBlank()) {
            throw new IllegalArgumentException("Beer name must not be null or blank.");
        }
        List<Beer> available = stock.get(beerName);
        if (available == null || available.isEmpty()) {
            throw new NoSuchElementException("Beer not found in stock: " + beerName);
        }
        if (quantity > available.size()) {
            throw new IllegalArgumentException(
                    "Requested %d units of but only %d available.".formatted(quantity, available.size()));
        }
        List<Beer> retrieved = new ArrayList<>(available.subList(0, quantity));
        available.subList(0, quantity).clear();
        return retrieved;
    }
    public int calculateProductionCapacity(List<DayOfWeek> days) {
        if (masterBrewer == null) {
            throw new IllegalStateException("No master brewer assigned to the brewery.");
        }
        if (days == null || days.isEmpty()) {
            return 0;
        }
        return masterBrewer.calculateProductionCapacity(days, helperBrewers);
    }
    public List<String> findMaturedBeers() {
        LocalDate cutoffDate = LocalDate.now().minusDays(180);
        return stock.values().stream()
                .flatMap(Collection::stream)
                .filter(beer -> beer.getProductionDate().isBefore(cutoffDate))
                .map(Beer::getName)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<String> findOptimalProductionDays(String startDayName, String endDayName) {
        DayOfWeek startDay = parseDayOfWeek(startDayName);
        DayOfWeek endDay = parseDayOfWeek(endDayName);
        List<DayOfWeek> daysInRange = getDaysInRange(startDay, endDay);
        if (daysInRange.isEmpty()) {
            return Collections.emptyList();
        }
        Map<DayOfWeek, Integer> helperCountByDay = daysInRange.stream()
                .collect(Collectors.toMap(day -> day, this::countAvailableHelpers));
        int maxHelpers = helperCountByDay.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        return helperCountByDay.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHelpers)
                .map(Map.Entry::getKey)
                .sorted()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }
    private int countAvailableHelpers(DayOfWeek day) {
        return (int) helperBrewers.stream()
                .filter(helper -> helper.isAvailableOn(day))
                .count();
    }
    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }
    private List<DayOfWeek> getDaysInRange(DayOfWeek start, DayOfWeek end) {
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek current = start;
        while (true) {
            days.add(current);
            if (current == end) {
                break;
            }
            current = current.plus(1);
            if (days.size() > 7) {
                break;
            }
        }
        return days;
    }
}