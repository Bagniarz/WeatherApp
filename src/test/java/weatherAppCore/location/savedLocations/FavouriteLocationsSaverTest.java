package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.core.type.TypeReference;
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

class FavouriteLocationsSaverTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final FavouriteLocationsSaver saver = new FavouriteLocationsSaver(mapper);
    private final Map<Integer, Location> map = new HashMap<>();
    private Map<Integer, Location> testMap = new HashMap<>();
    private final LocationFactory locationFactory = new LocationFactory();
    private final CoordinatesFactory coordinatesFactory = new CoordinatesFactory();

    @BeforeEach
    void prepareMaps() {
        map.clear();
        testMap.clear();
    }

    @Test
    void testCreateJSONFile_WithSingleKey_NotNull() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(20, 20), "Test01");
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/savedLocationsSaveTest01.json");
        map.put(1, test01);
        saver.createJSONFile(map, file);
        testMap = mapper.readValue(file, new TypeReference<>() {});

        assertNotNull(testMap);
    }

    @Test
    void testCreateJSONFile_WithSingleKey_Equals() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(25.02, 28.3), "Test01");
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/savedLocationsSaveTest01.json");
        map.put(1, test01);
        saver.createJSONFile(map, file);
        testMap = mapper.readValue(file, new TypeReference<>() {});

        assertEquals(testMap, map);
    }

    @Test
    void testCreateJSONFile_WithManyKeys_NotNull() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(25.02, 28.3), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(23.2, -28.3), "Test02");
        Location test03 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(27.02, 28.3), "Test03");
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/savedLocationsSaveTest02.json");
        map.put(1, test01);
        map.put(2, test02);
        map.put(3, test03);

        saver.createJSONFile(map, file);
    }

    @Test
    void testCreateJSONFile_WithManyKeys_Equals() throws IOException {
        Location test01 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(25.02, 28.3), "Test01");
        Location test02 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(23.2, -28.3), "Test02");
        Location test03 = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(27.02, 28.3), "Test03");
        File file = new File("src/test/java/weatherAppCore/location/savedLocations/savedLocationsTestStorage/savedLocationsSaveTest02.json");
        map.put(1, test01);
        map.put(2, test02);
        map.put(3, test03);

        saver.createJSONFile(map, file);
        testMap = mapper.readValue(file, new TypeReference<>() {});
        assertEquals(testMap, map);
    }
}