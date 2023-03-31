package weatherAppCore.weather;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Date;
@JsonPOJOBuilder
public class WeatherBuilder {
    public Weather build(Date date, Date sunRise, Date sunSet, int weatherCode, int temperature, int precipitation, float windSpeed) {
        return new Weather(date, sunRise, sunSet, weatherCode, temperature, precipitation, windSpeed);
    }
}
