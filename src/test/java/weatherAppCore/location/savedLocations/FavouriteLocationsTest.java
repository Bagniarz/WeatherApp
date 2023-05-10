package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.exceptions.DuplicateLocationException;
import weatherAppCore.exceptions.MaximumCapacityException;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;
class FavouriteLocationsTest {

    private final LocationFactory locationFactory = new LocationFactory();
    private final CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
    private final FavouriteLocations favouriteLocations = new FavouriteLocations(new HashMap<>(), new FavouriteLocationsSaver(new ObjectMapper()));
    private final LinkedHashMap<Integer, Location> map = new LinkedHashMap<>();

    @BeforeEach
    void prepareMap() {
        favouriteLocations.getMap().clear();
        map.clear();
    }

    @Test
    void testAddLocation_throwsMaximumCapacityException() {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(21,21), "Test02");
        Location test03 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(22,22), "Test03");
        Location test04 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(22,22), "Test04");
        Location test05 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(22,22), "Test05");

        Location testAdd = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(23,23), "Test06");
        favouriteLocations.getMap().put(1, test01);
        favouriteLocations.getMap().put(2, test02);
        favouriteLocations.getMap().put(3, test03);
        favouriteLocations.getMap().put(4, test04);
        favouriteLocations.getMap().put(5, test05);

        assertThrows(MaximumCapacityException.class, () -> favouriteLocations.addLocation(testAdd));
    }

    @Test
    void testAddLocation_throwsDuplicateLocationException() {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        favouriteLocations.getMap().put(1, test01);

        assertThrows(DuplicateLocationException.class, () -> favouriteLocations.addLocation(test01));
    }

    @Test
    void testAddLocation_NotNull() throws MaximumCapacityException, DuplicateLocationException, IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        favouriteLocations.addLocation(test01);

        assertNotNull(favouriteLocations.getMap());
    }

    @Test
    void testAddLocation_Equals() throws MaximumCapacityException, DuplicateLocationException, IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        favouriteLocations.addLocation(test01);

        map.put(1, test01);

        assertEquals(map, favouriteLocations.getMap());
    }

    @Test
    void testRemoveLocation_Equals() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(21,21), "Test02");
        favouriteLocations.getMap().put(1, test01);
        favouriteLocations.getMap().put(2, test02);

        map.put(2, test02);
        favouriteLocations.removeLocation(1);

        assertEquals(map, favouriteLocations.getMap());
    }

    @Test
    void testRemoveLocation_NotNull() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20,20), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(21,21), "Test02");
        favouriteLocations.getMap().put(1, test01);
        favouriteLocations.getMap().put(2, test02);

        favouriteLocations.removeLocation(1);

        assertNotNull(favouriteLocations.getMap());
    }
}