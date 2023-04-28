package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.settings.SettingsFactory;

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
        assertNotNull(provider.getWeatherList(locationFactory.buildLocation(coordinatesFactory.buildCoordinates(52, 21), "TEST")));
    }

    @Test
    void testGetWeatherList_NotEmpty_False() {
        WeatherForecastProvider provider = new WeatherForecastProvider(factory.createDefaultSettings(), mapper);
        assertFalse(provider.getWeatherList(locationFactory.buildLocation(coordinatesFactory.buildCoordinates(52, 21), "TEST")).isEmpty());
    }
}