package weatherAppCore.location;

import weatherAppCore.coordinates.Coordinates;
import org.junit.jupiter.api.Test;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void TestBuildLocationUsingFactoryMethod() {
        Location location = new Location(new Coordinates(20, 20), "TEST");
        LocationFactory locationFactory = new LocationFactory();
        assertEquals(location, locationFactory.buildLocation(new Coordinates(20,20), "TEST"));
    }
}