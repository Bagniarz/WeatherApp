package weatherAppCore.location;

import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.Coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    void buildLocation_FactoryMethod() {
        Location location = new Location(new Coordinates(20, 20), "TEST");
        LocationFactory locationFactory = new LocationFactory();
        assertEquals(location, locationFactory.buildLocation(new Coordinates(20,20), "TEST"));
    }
}