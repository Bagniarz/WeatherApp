package weatherAppCore.dataRetrieval.geocodingResponse;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class GeocodingResponseObj {
    String name, country, state;
    double latitude, longitude;
}
