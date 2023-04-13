package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.exceptions.internalServerException.InternalServerException;
import weatherAppCore.exceptions.wrongInputException.locationNotFoundException.LocationNotFoundException;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.SettingsFactory;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@Value
class GeocodingTest {

    ObjectMapper mapper;

    @Test
    void importCoordinatesData_CityName_Country() throws LocationNotFoundException, InternalServerException {
        Geocoding geocoding = Geocoding.builder().client(HttpClient.newBuilder().build()).build();
        SettingsFactory settingsFactory = new SettingsFactory();
        Settings settings = settingsFactory.createDefaultSettings();
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        String cityName = "London";
        String country = "England";
        Coordinates test01 = coordinatesFactory.buildCoordinates(51.508, -0.127);
        assertEquals(test01, geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString(), mapper));
    }

    @Test
    void importCoordinatesData_CityName() throws LocationNotFoundException, InternalServerException {
        Geocoding geocoding = Geocoding.builder().client(HttpClient.newBuilder().build()).build();
        SettingsFactory settingsFactory = new SettingsFactory();
        Settings settings = settingsFactory.createDefaultSettings();
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        String cityName = "Warsaw";
        String country = "";
        Coordinates test02 = coordinatesFactory.buildCoordinates(52.232, 21.007);
        assertEquals(test02, geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString(), mapper));
    }

    @Test
    void importCoordinatesData_ThrowsLocationNotFoundException() {
        Geocoding geocoding = Geocoding.builder().client(HttpClient.newBuilder().build()).build();
        SettingsFactory settingsFactory = new SettingsFactory();
        Settings settings = settingsFactory.createDefaultSettings();
        String cityName = "lhar";
        String country = "Poland";
        assertThrows(LocationNotFoundException.class, () -> geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString(), mapper));
    }
}