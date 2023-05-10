package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FavouriteLocationsProviderTest {

    private final Map<Integer, Location> map = new HashMap<>();
    private Map<Integer, Location> result;
    private final FavouriteLocationsProvider provider = new FavouriteLocationsProvider(new ObjectMapper());
    private final LocationFactory locationFactory = new LocationFactory();
    private final CoordinatesFactory coordinatesFactory = new CoordinatesFactory();

    @BeforeEach
    void prepareMaps() {
        map.clear();
        result.clear();
    }

    @Test
    void testCreateMap_WithSingleKey_NotNull() throws IOException {
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/favouriteLocationsProviderTest01.json");

        result = provider.createMap(file);

        assertNotNull(result);
    }

    @Test
    void testCreateMap_WithSingleKey_Equals() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(25.02, 28.3), "Test01");
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/favouriteLocationsProviderTest01.json");
        map.put(1, test01);

        result = provider.createMap(file);

        assertEquals(result, map);
    }

    @Test
    void testCreateMap_WithMultipleKeys_NotNull() throws IOException {
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/favouriteLocationsProviderTest02.json");

        result = provider.createMap(file);

        assertNotNull(result);
    }

    @Test
    void testCreateMap_WithMultipleKeys_Equals() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(25.02, 28.3), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(23.2, -28.3), "Test02");
        Location test03 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(27.02, 28.3), "Test03");
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/favouriteLocationsProviderTest02.json");
        map.put(1, test01);
        map.put(2, test02);
        map.put(3, test03);

        result = provider.createMap(file);

        assertEquals(provider.createMap(file) , map);
    }
}