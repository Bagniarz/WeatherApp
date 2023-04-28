package weatherAppCore.location.savedLocations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.exceptions.DuplicateLocationException;
import weatherAppCore.exceptions.MaximumCapacityException;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class FavouriteLocationsTest {

    private final LocationFactory locationFactory = new LocationFactory();
    private final CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
    private final FavouriteLocations favouriteLocations = new FavouriteLocations(new HashMap<>());

    @BeforeEach
    void prepareMap() {
        favouriteLocations.getMap().clear();
    }

    @Test
    void testAddLocation_throwsMaximumCapacityException() {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(21,21), "Test02");
        Location test03 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(22,22), "Test03");
        Location testAdd = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(23,23), "Test04");
        favouriteLocations.getMap().put("Test01", test01);
        favouriteLocations.getMap().put("Test02", test02);
        favouriteLocations.getMap().put("Test03", test03);


        assertThrows(MaximumCapacityException.class, () -> favouriteLocations.addLocation(testAdd));
    }

    @Test
    void testAddLocation_throwsDuplicateLocationException() {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        favouriteLocations.getMap().put("Test01", test01);

        assertThrows(DuplicateLocationException.class, () -> favouriteLocations.addLocation(test01));
    }

    @Test
    void testAddLocation_NotNull() throws MaximumCapacityException, DuplicateLocationException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        favouriteLocations.addLocation(test01);

        assertNotNull(favouriteLocations.getMap());
    }

    @Test
    void testAddLocation_Equals() throws MaximumCapacityException, DuplicateLocationException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        favouriteLocations.addLocation(test01);

        Map<String, Location> map = new HashMap<>();
        map.put("Test01", test01);

        assertEquals(map, favouriteLocations.getMap());
    }

    @Test
    void testRemoveLocation_Equals() {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(21,21), "Test02");
        favouriteLocations.getMap().put("Test01", test01);
        favouriteLocations.getMap().put("Test02", test02);

        Map<String, Location> map = new HashMap<>();
        map.put("Test02", test02);
        favouriteLocations.removeLocation(test01);

        assertEquals(map, favouriteLocations.getMap());
    }

    @Test
    void testRemoveLocation_NotNull() {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(21,21), "Test02");
        favouriteLocations.getMap().put("Test01", test01);
        favouriteLocations.getMap().put("Test02", test02);

        favouriteLocations.removeLocation(test01);

        assertNotNull(favouriteLocations.getMap());
    }
}