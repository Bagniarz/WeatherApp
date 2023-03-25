package menu.weather.builder;

import menu.location.Location;
import menu.weather.Builder;
import menu.weather.Weather;
import menu.weather.components.*;

import java.util.Date;

public class WeatherBuilder implements Builder {
    private Location location;
    private Date date;
    private Temperature temperature;
    private Precipitation precipitation;
    private WeatherCode weatherCode;
    private SunTime sunTime;
    private WindSpeed windSpeed;

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    @Override
    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }

    @Override
    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    @Override
    public void setSunTime(SunTime sunTime) {
        this.sunTime = sunTime;
    }

    @Override
    public void setWindSpeed(WindSpeed windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Weather getResult() {
        return new Weather(location, date, temperature, precipitation, weatherCode, sunTime, windSpeed);
    }
}
