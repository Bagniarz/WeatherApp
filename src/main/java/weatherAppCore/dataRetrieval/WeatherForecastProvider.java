package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import weatherAppCore.dataRetrieval.forecastResponse.ForecastResponse;
import weatherAppCore.location.Location;
import weatherAppCore.settings.Settings;
import weatherAppCore.weather.Weather;
import weatherAppCore.weather.WeatherBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Value
public class WeatherForecastProvider {
    HttpClient client;
    Settings settings;

    public WeatherForecastProvider(Settings settings) {
        this(HttpClient.newHttpClient(), settings);
    }

    public WeatherForecastProvider(HttpClient client , Settings settings) {
        this.client = client;
        this.settings = settings;
    }

    public HttpRequest createRequest(Location location) {
        HttpRequest request = null;
        try {
            request = HttpRequest
                    .newBuilder(new URI("https://api.open-meteo.com/v1/forecast?latitude="
                            + location.coordinates().latitude() + "&longitude="
                            + location.coordinates().longitude()
                            + "&daily=temperature_2m_max,precipitation_probability_max,weathercode,sunrise,sunset,windspeed_10m_max" +
                            "&forecast_days=" + settings.getDays() + "&timezone=auto"))
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return request;
    }

    public HttpResponse<String> getResponse(Location location) {
        HttpResponse<String> response = null;
        try {
            response = client.send(createRequest(location), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public ForecastResponse getForecastResponse(HttpResponse<String> response) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ForecastResponse forecastResponse = null;
        try {
            forecastResponse = mapper.readValue(response.body(), ForecastResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(forecastResponse);
        return forecastResponse;
    }

    public List<Weather> getWeatherList(Location location) {
        List<Weather> list = new ArrayList<>(settings.getDays());
        ForecastResponse forecastResponse = getForecastResponse(getResponse(location));
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        for (int i = 0; i < settings.getDays(); i++) {
            Weather weather = weatherBuilder.build(
                    forecastResponse.daily().time().get(i),
                    forecastResponse.daily().sunrise().get(i),
                    forecastResponse.daily().sunset().get(i),
                    forecastResponse.daily().weathercode().get(i),
                    forecastResponse.daily().temperature_2m_max().get(i),
                    forecastResponse.daily().precipitation_probability_max().get(i),
                    forecastResponse.daily().windspeed_10m_max().get(i),
                    forecastResponse.daily_units(),
                    "°C",
                    false);
            list.add(weather);
            System.out.println(list);
        }
        return list;
    }
}
