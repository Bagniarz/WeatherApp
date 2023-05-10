package weatherAppCore.location.savedLocations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.DuplicateLocationException;
import weatherAppCore.exceptions.MaximumCapacityException;
import weatherAppCore.location.Location;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FavouriteLocations {
    Map<Integer, Location> map;
    FavouriteLocationsSaver saver;

    public void addLocation(Location location) throws DuplicateLocationException, MaximumCapacityException, IOException {
        if (map.containsValue(location)) throw new DuplicateLocationException();
        if (map.size() >= 5) throw new MaximumCapacityException();
        map.put(map.size() + 1, location);
        saver.createJSONFile(map, new File("src/main/java/weatherAppCore/location/savedLocations/savedLocationsStorage/savedLocations.json"));
    }

    public void removeLocation(int number) throws IOException {
        map.remove(number);
        saver.createJSONFile(map, new File("src/main/java/weatherAppCore/location/savedLocations/savedLocationsStorage/savedLocations.json"));
    }
}
