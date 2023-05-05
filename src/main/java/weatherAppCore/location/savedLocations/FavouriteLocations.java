package weatherAppCore.location.savedLocations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.DuplicateLocationException;
import weatherAppCore.exceptions.MaximumCapacityException;
import weatherAppCore.location.Location;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FavouriteLocations {
    Map<Integer, Location> map;

    public void addLocation(Location location) throws DuplicateLocationException, MaximumCapacityException {
        if (map.containsValue(location)) throw new DuplicateLocationException();
        if (map.size() >= 5) throw new MaximumCapacityException();
        map.put(map.size() + 1, location);
    }

    public void removeLocation(int number) {
        map.remove(number);
    }
}
