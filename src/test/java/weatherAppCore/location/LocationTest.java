package weatherAppCore.location;

import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.CoordinatesFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    private final CoordinatesFactory factory = new CoordinatesFactory();

    @Test
    void buildLocation_FactoryMethod() {
        Location location = new Location(factory.buildCoordinates(20, 20), "TEST");
        LocationFactory locationFactory = new LocationFactory();
        assertEquals(location, locationFactory.buildLocation(factory.buildCoordinates(20,20), "TEST"));
    }
}