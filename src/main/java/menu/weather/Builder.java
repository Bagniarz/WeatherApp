package menu.weather;

import menu.location.Location;
import menu.weather.components.*;

import java.util.Date;

public interface Builder {
    void setLocation(Location location);
    void setDate(Date date);
    void setTemperature(Temperature temperature);
    void setPrecipitation(Precipitation precipitation);
    void setWeatherCode(WeatherCode weatherCode);
    void setSunTime(SunTime sunTime);
    void setWindSpeed(WindSpeed windSpeed);
}
