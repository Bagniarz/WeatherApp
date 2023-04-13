package weatherAppCore.location;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import weatherAppCore.coordinates.Coordinates;

@Value
@Jacksonized
public class Location {
    Coordinates coordinates;
    String cityName;
}
