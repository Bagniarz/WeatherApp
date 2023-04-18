package weatherAppCore.weather;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@JsonPOJOBuilder
public class WeatherBuilder {
    public Weather build(String date, String sunrise, String sunset,
                         int weatherCode, int precipitation, double temperature,
                         float windSpeed, UnitInfo unitInfo) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        return new Weather(date, sunrise, sunset,
                weatherCode,
                precipitation, Integer.parseInt(df.format(temperature)),
                windSpeed,
                unitInfo);
    }
}
