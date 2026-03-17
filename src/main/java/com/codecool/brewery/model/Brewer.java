package com.codecool.brewery.model;
import java.time.DayOfWeek;
public abstract class Brewer {
    private final String name;
    private final int yearsOfExperience;
    protected Brewer(String name, int yearsOfExperience) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Brewer name must not be null or blank.");
        }
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience must not be negative.");
        }
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }
    public String getName() {
        return name;
    }
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    public abstract boolean isAvailableOn(DayOfWeek day);
    public abstract String getRole();
    @Override
    public String toString() {
        return "%s[%s, %d yrs]".formatted(getRole(), name, yearsOfExperience);
    }
}