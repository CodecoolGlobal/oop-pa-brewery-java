package com.codecool.brewery;
import com.codecool.brewery.model.Brewer.HelperBrewer;
import com.codecool.brewery.model.Beer.*;
import com.codecool.brewery.model.Brewer.MasterBrewer;
import com.codecool.brewery.service.Brewery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class BreweryTest {
    private Brewery brewery;
    private MasterBrewer masterBrewer;
    private HelperBrewer helper1;
    private HelperBrewer helper2;
    private HelperBrewer helper3;
    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");
        masterBrewer = new MasterBrewer("Master John", 15);
        helper1 = new HelperBrewer("Helper Alice", 3,
                List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        helper2 = new HelperBrewer("Helper Bob", 2,
                List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        helper3 = new HelperBrewer("Helper Charlie", 4,
                List.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        brewery.addBrewer(masterBrewer);
        brewery.addBrewer(helper1);
        brewery.addBrewer(helper2);
        brewery.addBrewer(helper3);
    }
    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY));
        assertEquals(2, capacity, "Production capacity should equal number of helpers on Monday");
    }
    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {
        // Monday: Alice, Bob -> 2; Thursday: Bob, Charlie -> 2; Total: 4
        int capacity = brewery.calculateProductionCapacity(
                List.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));
        assertEquals(4, capacity);
    }
    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {
        assertThrows(IllegalArgumentException.class,
                () -> brewery.findOptimalProductionDays("INVALID_DAY", "FRIDAY"));
    }
    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        brewery.addBeerToStock(new Ale("Fresh", 5.0, LocalDate.now().minusDays(100)));
        brewery.addBeerToStock(new Ale("Old Ale", 6.0, LocalDate.now().minusDays(200)));
        brewery.addBeerToStock(new Lager("Old Lager", 5.5, LocalDate.now().minusDays(190)));
        List<String> expired = brewery.findMaturedBeers();
        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }
    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        // Mon: Alice,Bob=2; Tue: Alice,Charlie=2; Wed: Alice,Charlie=2; Thu: Bob,Charlie=2; Fri: Bob=1
        List<String> optimal = brewery.findOptimalProductionDays("MONDAY", "FRIDAY");
        assertEquals(4, optimal.size());
        assertTrue(optimal.contains("MONDAY"));
        assertTrue(optimal.contains("TUESDAY"));
        assertTrue(optimal.contains("WEDNESDAY"));
        assertTrue(optimal.contains("THURSDAY"));
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
        assertTrue(lager.canBeStoredAt(2.0));
        assertFalse(lager.canBeStoredAt(8.0));
        assertTrue(ale.canBeStoredAt(18.0));
        assertFalse(ale.canBeStoredAt(2.0));
        assertFalse(ale.canBeStoredAt(30.0));
    }
    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        assertTrue(helper1.isAvailableOn(DayOfWeek.MONDAY));
        assertTrue(helper1.isAvailableOn(DayOfWeek.TUESDAY));
        assertTrue(helper1.isAvailableOn(DayOfWeek.WEDNESDAY));
        assertFalse(helper1.isAvailableOn(DayOfWeek.THURSDAY));
        assertFalse(helper1.isAvailableOn(DayOfWeek.FRIDAY));
        assertFalse(helper1.isAvailableOn(DayOfWeek.SATURDAY));
        assertFalse(helper1.isAvailableOn(DayOfWeek.SUNDAY));
        assertTrue(helper2.isAvailableOn(DayOfWeek.MONDAY));
        assertFalse(helper2.isAvailableOn(DayOfWeek.TUESDAY));
        assertTrue(helper2.isAvailableOn(DayOfWeek.THURSDAY));
        assertTrue(helper2.isAvailableOn(DayOfWeek.FRIDAY));
    }
}