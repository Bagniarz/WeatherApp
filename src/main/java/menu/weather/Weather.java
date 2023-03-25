package menu.weather;

import menu.location.Location;
import menu.weather.components.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Weather {
    private final Location location;
    private final Date date;
    private final Temperature temperature;
    private final Precipitation precipitation;
    private final WeatherCode code;
    private final SunTime sunTime;
    private final WindSpeed windSpeed;

    public Weather(Location location, Date date, Temperature temperature, Precipitation precipitation, WeatherCode code, SunTime sunTime, WindSpeed windSpeed) {
        this.location = location;
        this.date = date;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.code = code;
        this.sunTime = sunTime;
        this.windSpeed = windSpeed;
    }

    public Location getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public WeatherCode getCode() {
        return code;
    }

    public SunTime getSunTime() {
        return sunTime;
    }

    public WindSpeed getWindSpeed() {
        return windSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(date, weather.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy");
        return "Weather{" +
                "location=" + location +
                ", date=" + simpleDateFormat.format(date) +
                ", temperature=" + temperature +
                ", precipitation=" + precipitation +
                ", code=" + code +
                ", sunTime=" + sunTime +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
