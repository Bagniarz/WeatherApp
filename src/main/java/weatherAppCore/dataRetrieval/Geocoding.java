package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.ObjectMapper;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Geocoding {
    public static double[] importCoordinatesData(String[] input) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        String[] strings = new String[5];
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://api.api-ninjas.com/v1/geocoding?city=" + input[0] + "&country=" + input[1]))
                    .header("X-Api-Key", "FcrzE0MbWTgJsS3zUNv+AA==btj0ZifhJZbfz3sV")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Location location = mapper.readValue(response.body(), Location.class);
            strings = response.body().substring(0, response.body().indexOf("}") + 1).split(",");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return findCoordinates(strings);
    }

    public static double[] findCoordinates(String[] input) {
        double[] result = new double[2];
        for (String element : input) {
            if (element.contains("latitude"))  {
                String s = element.substring(element.indexOf(":") + 1);
                result[0] = Double.parseDouble(s);
            }
            if (element.contains("longitude")) {
                String s = element.substring(element.indexOf(":") + 1);
                result[1] = Double.parseDouble(s);
            }
        }
        return result;
    }
}
