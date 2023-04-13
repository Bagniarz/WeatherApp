package weatherAppCore.coordinates;

import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
public class Coordinates {
    double latitude, longitude;
}
