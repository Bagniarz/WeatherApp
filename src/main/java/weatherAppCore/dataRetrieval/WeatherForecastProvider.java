package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import weatherAppCore.location.Location;
import weatherAppCore.settings.Settings;
import weatherAppCore.weather.Weather;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherForecastProvider {
    private final HttpClient client;
    private final Settings settings;

    public WeatherForecastProvider(Settings settings) {
        this(HttpClient.newHttpClient(), settings);
    }

    public WeatherForecastProvider(HttpClient client , Settings settings) {
        this.client = client;
        this.settings = settings;
    }
//TODO NOTWORKING METHOD
    public List<Weather> getWeatherList(Location location) {
        List<Weather> list = new ArrayList<>(settings.getDays());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://api.open-meteo.com/v1/forecast?latitude="
                            + location.coordinates().latitude() + "&longitude="
                            + location.coordinates().longitude()
                            + "&daily=temperature_2m_max,precipitation_probability_max,weathercode,sunrise,sunset,windspeed_10m_max" +
                            "&forecast_days=" + settings.getDays() + "&timezone=auto"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            list = Arrays.asList(mapper.readValue(body, Weather[].class));
            System.out.println(list);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
