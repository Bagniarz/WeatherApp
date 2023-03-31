package weatherAppCore.weather;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;

public
@Builder
@JsonDeserialize(builder = Weather.WeatherBuilder.class)
record Weather(@EqualsAndHashCode.Include Date time, Date sunrise, Date sunset, int weatherCode,
               int temperature_2m_max, int precipitation_probability_max, float windSpeed_10_max) {
}
