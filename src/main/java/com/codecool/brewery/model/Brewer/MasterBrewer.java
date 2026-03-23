package com.codecool.brewery.model.Brewer;

import java.time.DayOfWeek;
import java.util.List;
public final class MasterBrewer extends Brewer {
    public MasterBrewer(String name, int yearsOfExperience) {
        super(name, yearsOfExperience);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        if (day == null) {
            throw new IllegalArgumentException("Day must not be null.");
        }
        return true;
    }



    public int calculateProductionCapacity(List<DayOfWeek> days, List<HelperBrewer> helpers) {
        if (days == null || helpers == null) {
            throw new IllegalArgumentException("Days and helpers must not be null.");
        }
        return days.stream()
                .mapToInt(day -> (int) helpers.stream()
                        .filter(helper -> helper.isAvailableOn(day))
                        .count())
                .sum();
    }
}