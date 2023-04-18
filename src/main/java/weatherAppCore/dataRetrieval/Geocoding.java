package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.dataRetrieval.geocodingResponse.GeocodingResponseObj;
import weatherAppCore.exceptions.InternalAPIConnectionException;
import weatherAppCore.exceptions.wrongInputException.components.LocationNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Geocoding {
    HttpClient client;
    String response;
    final ObjectMapper mapper;

    private List<GeocodingResponseObj> createGeocodingResponse(HttpResponse<String> response) throws LocationNotFoundException, InternalAPIConnectionException {
        List<GeocodingResponseObj> list;
        try {
           list = mapper.readValue(response.body(), new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new InternalAPIConnectionException(e);
        }
        if (list == null || list.isEmpty()) {
            throw new LocationNotFoundException();
        }
        return list;
    }

    private HttpResponse<String> getResponse(HttpRequest request) throws LocationNotFoundException {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response == null || response.body().length() < 3) {
            throw new LocationNotFoundException();
        }
        return response;
    }

    private HttpRequest createRequest(String cityName, String country, String apiKey) {
        HttpRequest request;
        try {
            request = HttpRequest
                    .newBuilder(new URI("https://api.api-ninjas.com/v1/geocoding?city=" + cityName + "&country=" + country))
                    .header("X-Api-Key", apiKey)
                    .header("accept", "application/json")
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return request;
    }

    public Coordinates importCoordinates(String cityName, String country, String apiKey) throws LocationNotFoundException, InternalAPIConnectionException {
        configureMapper();
        List<GeocodingResponseObj> list = createGeocodingResponse(getResponse(createRequest(cityName, country, apiKey)));
        if (list.isEmpty()) {
            throw new LocationNotFoundException();
        }
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        return coordinatesFactory.buildCoordinates(list.get(0).getLatitude(), list.get(0).getLongitude());
    }

    private void configureMapper() {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
}
