package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.settings.SettingsFactory;
import weatherAppCore.weather.Weather;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherForecastProviderTest {

    private final SettingsFactory factory = new SettingsFactory();
    private final ObjectMapper mapper = new ObjectMapper();
    private final CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
    private final LocationFactory locationFactory = new LocationFactory();

    @Test
    void testGetWeatherList_NotNull() {
        WeatherForecastProvider provider = new WeatherForecastProvider(factory.createDefaultSettings(), mapper);
        List<Weather> result = provider.getWeatherList(locationFactory.buildLocation(coordinatesFactory.buildCoordinates(52, 21), "TEST"));
        assertNotNull(result);
    }

    @Test
    void testGetWeatherList_NotEmpty_False() {
        WeatherForecastProvider provider = new WeatherForecastProvider(factory.createDefaultSettings(), mapper);

        boolean result = provider.getWeatherList(locationFactory.buildLocation(coordinatesFactory.buildCoordinates(46, 23), "TEST")).isEmpty();

        assertFalse(result);
    }
}