package com.codecool.brewery.model.Beer;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public abstract class Beer {
    private final String name;
    private final double alcoholContent;
    private final LocalDate productionDate;

    protected Beer(String name, double alcoholContent, LocalDate productionDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Beer name must not be null or blank.");
        }
        if (alcoholContent < 0 || alcoholContent > 100) {
            throw new IllegalArgumentException("Alcohol content must be between 0 and 100.");
        }
        if (productionDate == null) {
            throw new IllegalArgumentException("Production date must not be null.");
        }
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

    public abstract int getFermentationDays();
    public abstract boolean canBeStoredAt(double temperatureCelsius);

    public long getDaysUntilReady() {
        LocalDate readyDate = productionDate.plusDays(getFermentationDays());
        return ChronoUnit.DAYS.between(LocalDate.now(), readyDate);
    }
    @Override
    public String toString() {
        return "%s(%s, %.1f%%, produced %s)".formatted(
                getClass().getSimpleName(), name, alcoholContent, productionDate);
    }
}