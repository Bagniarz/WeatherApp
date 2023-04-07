package weatherAppCore.dataRetrieval;

import lombok.Builder;
import lombok.Data;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.coordinates.CoordinatesFactory;
import weatherAppCore.exceptions.LocationNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
@Builder
public class Geocoding {
    HttpClient client;
    String response;

    private void initResponse(String cityName, String country) throws LocationNotFoundException {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://api.api-ninjas.com/v1/geocoding?city=" + cityName + "&country=" + country))
                    .header("X-Api-Key", "FcrzE0MbWTgJsS3zUNv+AA==btj0ZifhJZbfz3sV")
                    .build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.body().length() < 3) throw new LocationNotFoundException();
            setResponse(httpResponse.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Coordinates importCoordinates(String cityName, String country) throws LocationNotFoundException {
        initResponse(cityName, country);
        String[] strings;
        strings = response.substring(0, response.indexOf("}") + 1).split(",");
        return findCoordinates(strings);
    }

    public Coordinates findCoordinates(String[] input) {
        CoordinatesFactory coordinatesFactory = new CoordinatesFactory();
        double x = 0 , y = 0;
        for (String element : input) {
            if (element.contains("latitude"))  {
                String s = element.substring(element.indexOf(":") + 1);
                x = Double.parseDouble(s);
            }
            if (element.contains("longitude")) {
                String s = element.substring(element.indexOf(":") + 1);
                y = Double.parseDouble(s);
            }
        }
        return coordinatesFactory.buildCoordinates(x, y);
    }
}
