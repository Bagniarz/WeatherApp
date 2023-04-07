package weatherAppCore.dataRetrieval;

import org.junit.jupiter.api.Test;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.exceptions.LocationNotFoundException;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.*;

class GeocodingTest {

    @Test
    void importCoordinatesData_CityName_Country() throws LocationNotFoundException {
        Geocoding geocoding = Geocoding.builder().client(HttpClient.newBuilder().build()).build();
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        String cityName = "London";
        String country = "England";
        Coordinates test01 = coordinatesFactory.buildCoordinates(51.508, -0.127);
        assertEquals(test01, geocoding.importCoordinates(cityName, country));
    }

    @Test
    void importCoordinatesData_CityName() throws LocationNotFoundException {
        Geocoding geocoding = Geocoding.builder().client(HttpClient.newBuilder().build()).build();
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        String cityName = "Warsaw";
        String country = "";
        Coordinates test02 = coordinatesFactory.buildCoordinates(52.232, 21.007);
        assertEquals(test02, geocoding.importCoordinates(cityName, country));
    }

    @Test
    void importCoordinatesData_ThrowsLocationNotFoundException() {
        Geocoding geocoding = Geocoding.builder().client(HttpClient.newBuilder().build()).build();
        String cityName = "lhar";
        String country = "Poland";
        assertThrows(LocationNotFoundException.class, () -> geocoding.importCoordinates(cityName, country));
    }
}