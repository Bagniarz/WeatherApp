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
import weatherAppCore.exceptions.internalServerException.InternalServerException;
import weatherAppCore.exceptions.wrongInputException.locationNotFoundException.LocationNotFoundException;

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
    String response, excMessLocationNotFound, excMessInternalServerException;

    private List<GeocodingResponseObj> createGeocodingResponse(HttpResponse<String> response, ObjectMapper mapper) throws LocationNotFoundException, InternalServerException {
        List<GeocodingResponseObj> list;
        try {
           list = mapper.readValue(response.body(), new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            throw new InternalServerException(excMessInternalServerException);
        }
        if (list == null || list.isEmpty()) {
            throw new LocationNotFoundException(excMessLocationNotFound);
        }
        return list;
    }

    public Coordinates importCoordinates(String cityName, String country, String apiKey, ObjectMapper mapper) throws LocationNotFoundException, InternalServerException {
        List<GeocodingResponseObj> list = createGeocodingResponse(getResponse(createRequest(cityName, country, apiKey)), configureMapper(mapper));
        if (list.isEmpty()) {
            throw new LocationNotFoundException(excMessLocationNotFound);
        }
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        System.out.println(list);
        return coordinatesFactory.buildCoordinates(list.get(0).getLatitude(), list.get(0).getLongitude());
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
            throw new RuntimeException();
        }
        return request;
    }

    private HttpResponse<String> getResponse(HttpRequest request) throws LocationNotFoundException {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
        if (response == null || response.body().length() < 3) {
            throw new LocationNotFoundException(excMessLocationNotFound);
        }
        return response;
    }

    private ObjectMapper configureMapper(ObjectMapper mapper) {
        mapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper;
    }
}
