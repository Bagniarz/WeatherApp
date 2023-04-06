package weatherAppCore.dataRetrieval.forecastResponse;

import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;
import weatherAppCore.dataRetrieval.forecastResponse.components.WeatherForecast;

public record ForecastResponse(WeatherForecast daily, UnitInfo daily_units) {
}
