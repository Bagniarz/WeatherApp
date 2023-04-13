package weatherAppCore.dataRetrieval.forecastResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;
import weatherAppCore.dataRetrieval.forecastResponse.components.WeatherForecast;

@Value
@Builder
@Jacksonized
public class ForecastResponse {
    WeatherForecast daily;
    @JsonProperty("daily_units")
    UnitInfo dailyUnits;
}
