package weatherAppCore.coordinates;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(builderClassName = "CoordinatesFactory")
public class Coordinates {
    double latitude, longitude;
}
