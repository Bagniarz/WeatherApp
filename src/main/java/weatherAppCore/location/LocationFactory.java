package weatherAppCore.location;

import weatherAppCore.coordinates.Coordinates;

public class LocationFactory {
    public Location buildLocation(Coordinates coordinates, String cityName) {
        return new Location(coordinates, cityName);
    }
}
