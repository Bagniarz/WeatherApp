package weatherAppCore.weather;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;
import weatherAppCore.settings.WeatherInfoSettings;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
@Data
public class Weather {
//    TODO Change time, sunrise and sunset to LocalDate, LocalTime
    final String time, sunrise, sunset;
    final int weatherCode, precipitation_probability_max;
    int temperature_2m_max;
    final float windSpeed_10_max;
    final UnitInfo unitInfo;
    public void changeScale(WeatherInfoSettings weatherInfoSettings) {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        switch (weatherInfoSettings) {
            case CELSIUS -> {
                unitInfo.setTemperature("°C");
                setTemperature_2m_max(Integer.parseInt(df.format((temperature_2m_max - 32) * 0.5556)));
            }
            case FAHRENHEIT -> {
                unitInfo.setTemperature("°F");
                setTemperature_2m_max(Integer.parseInt( df.format((temperature_2m_max * 1.8) + 32)));
            }
        }
    }
}
