package weatherAppCore.dataRetrieval;

import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.WeatherInfoSettings;
import weatherAppCore.settings.language.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherForecastProviderTest {

    @Mock
    HttpResponse<String> mockResponse;
    @Mock
    HttpClient httpClient;

    @Test
    public void getBasicWeatherList() throws IOException, InterruptedException {
        LocationFactory locationFactory = new LocationFactory();
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        Settings settings = new Settings(WeatherInfoSettings.CELSIUS, Language.ENGLISH, 3);
        Location location = locationFactory.buildLocation(coordinatesFactory.buildCoordinates(52, 21), "Warsaw");
        WeatherForecastProvider weatherForecastProvider = new WeatherForecastProvider(httpClient, settings);
        weatherForecastProvider.getWeatherList(location);
        when(mockResponse.body()).thenReturn(anyString());
    }
}