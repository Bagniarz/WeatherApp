package weatherAppCore.location;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import weatherAppCore.coordinates.Coordinates;

@Value
@Jacksonized
@Builder(builderClassName = "LocationFactory")
public class Location {
    Coordinates coordinates;
    String cityName;
}
