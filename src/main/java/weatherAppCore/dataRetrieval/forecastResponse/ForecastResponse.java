package weatherAppCore.dataRetrieval.forecastResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;
import weatherAppCore.dataRetrieval.forecastResponse.components.WeatherForecast;

@Value
@Jacksonized
@Builder
public class ForecastResponse {
    WeatherForecast daily;
    @JsonProperty("daily_units")
    UnitInfo dailyUnits;
}
