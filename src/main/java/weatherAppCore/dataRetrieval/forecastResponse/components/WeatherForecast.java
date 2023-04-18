package weatherAppCore.dataRetrieval.forecastResponse.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class WeatherForecast {
    List<String> time, sunrise, sunset;
    @JsonProperty("temperature_2m_max")
    List<Integer> temperature;
    @JsonProperty("weathercode")
    List<Integer> weatherCode;
    @JsonProperty("precipitation_probability_max")
    List<Integer> precipitation;
    @JsonProperty("windspeed_10m_max")
    List<Float> windspeed;
}
