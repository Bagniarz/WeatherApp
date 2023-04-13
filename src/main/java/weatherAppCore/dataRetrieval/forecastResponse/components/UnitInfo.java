package weatherAppCore.dataRetrieval.forecastResponse.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
@Builder
@Jacksonized
public class UnitInfo {
    @JsonProperty("temperature_2m_max")
    String temperature;
    @JsonProperty("precipitation_probability_max")
    final String precipitation;
    @JsonProperty("windspeed_10m_max")
    final String windSpeed;
}
