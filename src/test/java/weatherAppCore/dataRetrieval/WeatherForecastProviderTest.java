package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.location.Location;
import weatherAppCore.settings.SettingsFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherForecastProviderTest {
    @Test
    void testGetWeatherList_NotNull() {
        SettingsFactory factory = new SettingsFactory();
        ObjectMapper mapper = new ObjectMapper();
        WeatherForecastProvider provider = new WeatherForecastProvider(factory.createDefaultSettings(), mapper);
        assertNotNull(provider.getWeatherList(new Location(new Coordinates(52, 21), "TEST")));
    }

    @Test
    void testGetWeatherList_NotEmpty_False() {
        SettingsFactory factory = new SettingsFactory();
        ObjectMapper mapper = new ObjectMapper();
        WeatherForecastProvider provider = new WeatherForecastProvider(factory.createDefaultSettings(), mapper);
        assertFalse(provider.getWeatherList(new Location(new Coordinates(52, 21), "TEST")).isEmpty());
    }
}