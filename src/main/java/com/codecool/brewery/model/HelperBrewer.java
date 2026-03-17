package com.codecool.brewery.model;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
public final class HelperBrewer extends Brewer {
    private final Set<DayOfWeek> workDays;
    public HelperBrewer(String name, int yearsOfExperience, List<DayOfWeek> workDays) {
        super(name, yearsOfExperience);
        if (workDays == null || workDays.isEmpty()) {
            throw new IllegalArgumentException("Helper brewer must have at least one work day.");
        }
        this.workDays = Collections.unmodifiableSet(EnumSet.copyOf(workDays));
    }
    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        if (day == null) {
            throw new IllegalArgumentException("Day must not be null.");
        }
        return workDays.contains(day);
    }
    @Override
    public String getRole() {
        return "HelperBrewer";
    }
    public Set<DayOfWeek> getWorkDays() {
        return workDays;
    }
}