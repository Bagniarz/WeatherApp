package weatherAppCore.dataRetrieval.forecastResponse.components;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UnitInfo {
    private String temperature_2m_max;
    private final String precipitation_probability_max, windspeed_10m_max;
}
