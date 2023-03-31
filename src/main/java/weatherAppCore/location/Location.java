package weatherAppCore.location;

import weatherAppCore.coordinates.Coordinates;

public record Location(Coordinates coordinates, String cityName) {
}
