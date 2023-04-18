package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.exceptions.InternalAPIConnectionException;
import weatherAppCore.exceptions.wrongInputException.components.LocationNotFoundException;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.SettingsFactory;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeocodingTest {

    private final Geocoding geocoding = Geocoding.builder()
            .client(HttpClient.newBuilder().build())
            .mapper(new ObjectMapper())
            .build();
    private final SettingsFactory settingsFactory = new SettingsFactory();
    private final CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
    private Settings settings;

    @BeforeEach
    void prepareSettings() {
        settings = settingsFactory.createDefaultSettings();
    }


    @Test
    void importCoordinatesData_CityName_Country() throws LocationNotFoundException, InternalAPIConnectionException {
        String cityName = "London";
        String country = "England";
        Coordinates test01 = coordinatesFactory.buildCoordinates(51.508, -0.127);
        assertEquals(test01, geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString()));
    }

    @Test
    void importCoordinatesData_CityName() throws LocationNotFoundException, InternalAPIConnectionException {
        String cityName = "Warsaw";
        String country = "";
        Coordinates test02 = coordinatesFactory.buildCoordinates(52.232, 21.007);
        assertEquals(test02, geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString()));
    }

    @Test
    void importCoordinatesData_ThrowsLocationNotFoundException() {
        String cityName = "lhar";
        String country = "Poland";
        assertThrows(LocationNotFoundException.class, () -> geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString()));
    }
}