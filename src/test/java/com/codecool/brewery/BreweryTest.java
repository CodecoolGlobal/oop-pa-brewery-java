package com.codecool.brewery;

import com.codecool.brewery.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreweryTest {

    private Brewery brewery;
    private Brewer masterBrewer;
    private Brewer helper1;
    private Brewer helper2;
    private Brewer helper3;

    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");

        // Set up brewers
        // TODO: master brewer: "Master John", 15 years experience
        // TODO: helper brewer: "Helper Alice", 3 years experience, available Mon, Tue, Wed
        // TODO: helper brewer: "Helper Bob", 2 years experience, available Mon, Thu, Fri
        // TODO: helper brewer: "Helper Charlie", 4 years experience, available Tue, Wed, Thu

        brewery.addBrewer(masterBrewer);
        brewery.addBrewer(helper1);
        brewery.addBrewer(helper2);
        brewery.addBrewer(helper3);
    }

    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {
        // Monday: helper1 and helper2 are available (2 helpers)
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY));
        assertEquals(2, capacity, "Production capacity should equal number of helpers on Monday");
    }

    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {
        // Monday: 2 helpers, Thursday: 2 helpers
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));
        assertEquals(4, capacity, "Should return sum: 2 + 2 = 4");

        // Monday: 2 helpers, Tuesday: 2 helpers, Friday: 1 helper
        capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.FRIDAY));
        assertEquals(5, capacity, "Should return sum: 2 + 2 + 1 = 5");
    }

    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {
        assertThrows(IllegalArgumentException.class,
                () -> brewery.calculateProductionCapacity(List.of(DayOfWeek.valueOf("FUNDAY"))),
                "Should throw IllegalArgumentException for invalid day name");
    }


    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        // Add fresh beer
        Beer freshBeer = new Lager("Fresh", 5.0, LocalDate.now().minusDays(100));
        brewery.addBeerToStock(freshBeer);

        // Add expired beers
        Beer expiredBeer1 = new Ale("Old Ale", 6.0, LocalDate.now().minusDays(200));
        Beer expiredBeer2 = new Lager("Old Lager", 5.5, LocalDate.now().minusDays(190));
        brewery.addBeerToStock(expiredBeer1);
        brewery.addBeerToStock(expiredBeer2);

        List<String> expired = brewery.findExpiredBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }

    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        // Monday: 2, Tuesday: 2, Wednesday: 2, Thursday: 2, Friday: 1
        List<String> optimal = brewery.findOptimalProductionDays("MONDAY", "FRIDAY");

        assertTrue(optimal.size() >= 1);
        // Should include days with 2 helpers, not Friday (1 helper)
        assertFalse(optimal.contains("FRIDAY"));
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {
        Lager lager = new Lager("Test Lager", 5.0, LocalDate.now());
        Ale ale = new Ale("Test Ale", 5.5, LocalDate.now());

        assertEquals(21, lager.getFermentationDays());
        assertEquals(14, ale.getFermentationDays());
    }

    @Test
    @DisplayName("Beer type has correct storage temperature")
    void testBeerStorageTemperature() {
        Lager lager = new Lager("Test Lager", 5.0, LocalDate.now());
        Ale ale = new Ale("Test Ale", 5.5, LocalDate.now());

        assertEquals(StorageTemperature.COLD, lager.getStorageTemperature());
        assertEquals(StorageTemperature.ROOM, ale.getStorageTemperature());
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        assertTrue(helper1.isAvailableOn(DayOfWeek.MONDAY));
        assertTrue(helper1.isAvailableOn(DayOfWeek.TUESDAY));
        assertFalse(helper1.isAvailableOn(DayOfWeek.FRIDAY));
    }

    @Test
    @DisplayName("Master brewer is available on all days")
    void testMasterBrewerAvailability() {
        for (DayOfWeek day : DayOfWeek.values()) {
            assertTrue(masterBrewer.isAvailableOn(day));
        }
    }

    @Test
    @DisplayName("Null safety: adding null brewer throws exception")
    void testNullBrewerThrowsException() {
        assertThrows(NullPointerException.class, () -> brewery.addBrewer(null));
    }

    @Test
    @DisplayName("Null safety: adding null beer throws exception")
    void testNullBeerThrowsException() {
        assertThrows(NullPointerException.class, () -> brewery.addBeerToStock(null));
    }

    @Test
    @DisplayName("Beer immutability: production date cannot be changed")
    void testBeerImmutability() {
        LocalDate originalDate = LocalDate.now().minusDays(10);
        Beer beer = new Lager("Test", 5.0, originalDate);

        // Get the production date
        LocalDate retrievedDate = beer.getProductionDate();

        // Verify it's the same
        assertEquals(originalDate, retrievedDate);

        // Beer should remain unchanged even if we try to modify the date
        assertEquals(originalDate, beer.getProductionDate());
    }
}
