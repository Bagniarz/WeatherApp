package weatherAppCore.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@AllArgsConstructor
@Data
public class Weather {
    final String time, sunrise, sunset;
    final int weatherCode, precipitation_probability_max;
    int temperature_2m_max;
    final float windSpeed_10_max;
    final UnitInfo unitInfo;
    String scale;
    boolean reversed;
    // For now ChangeScale will not be used ()
    //TODO Rework ChangeScale
    public void changeScale() {
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        if (reversed) {
            setScale("°C");
            setTemperature_2m_max(Integer.parseInt(df.format((temperature_2m_max - 32) * 0.5556)));
        } else {
            setScale("°F");
            setTemperature_2m_max(Integer.parseInt( df.format((temperature_2m_max * 1.8) + 32)));
        }
    }
}
