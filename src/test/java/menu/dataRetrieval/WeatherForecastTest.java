package menu.dataRetrieval;

import menu.coordinates.CoordinatesFactory;
import menu.location.LocationFactory;
import menu.settings.Settings;
import menu.settings.WeatherInfoSettings;
import menu.settings.language.Language;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastTest {

    @Test
    void getWeatherList() {
    }

    @Test
    void getBasicWeatherList() {
        LocationFactory locationFactory = new LocationFactory();
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        WeatherForecast weatherForecast = new WeatherForecast(Settings.getSettings
                (WeatherInfoSettings.CELSIUS, Language.ENGLISH, 2),
                locationFactory.buildLocation(coordinatesFactory.buildCoordinates(52.523, 13.423), "London"));
    }

    @Test
    void createBasicWeatherList() {
    }

    @Test
    void getAdvancedWeatherList() {
    }
}