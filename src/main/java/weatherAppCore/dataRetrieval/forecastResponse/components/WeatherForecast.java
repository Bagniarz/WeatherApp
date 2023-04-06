package weatherAppCore.dataRetrieval.forecastResponse.components;

import java.util.List;

public record WeatherForecast(List<String> time, List<String> sunrise, List<String> sunset,
                              List<Integer> temperature_2m_max, List<Integer> weathercode, List<Integer> precipitation_probability_max,
                              List<Float> windspeed_10m_max) {
}
