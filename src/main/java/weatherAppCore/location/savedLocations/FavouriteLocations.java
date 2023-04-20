package weatherAppCore.location.savedLocations;

import lombok.Value;
import weatherAppCore.exceptions.DuplicateLocationException;
import weatherAppCore.exceptions.MaximumCapacityException;
import weatherAppCore.location.Location;

import java.util.Map;

@Value
public class FavouriteLocations {
    Map<String, Location> map;

    public boolean addLocation(Location location) throws DuplicateLocationException, MaximumCapacityException {
        if (map.containsKey(location.getCityName())) throw new DuplicateLocationException();
        if (map.size() >= 3) throw new MaximumCapacityException();
        map.put(location.getCityName(), location);
        return map.containsKey(location.getCityName());
    }

    public boolean removeLocation(Location location) {
        map.remove(location.getCityName());
        return !map.containsKey(location.getCityName());
    }
}
