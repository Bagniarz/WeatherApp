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
        HttpRequest request;
        try {
            request = HttpRequest
                    .newBuilder(new URI("https://api.open-meteo.com/v1/forecast?latitude="
                            + location.getCoordinates().getLatitude() + "&longitude="
                            + location.getCoordinates().getLongitude()
                            + "&daily=temperature_2m_max,precipitation_probability_max,weathercode,sunrise,sunset,windspeed_10m_max" +
                            "&forecast_days=" + settings.getDays() + "&timezone=auto"))
                    .build();
        } catch (URISyntaxException e) {
           throw new RuntimeException();
        }
        return request;
    }

    public HttpResponse<String> getResponse(Location location) {
        HttpResponse<String> response;
        try {
            response = client.send(createRequest(location), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
        return response;
    }

    public ForecastResponse getForecastResponse(HttpResponse<String> response, ObjectMapper mapper) {
        ForecastResponse forecastResponse;
        try {
            forecastResponse = mapper.readValue(response.body(), ForecastResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
        return forecastResponse;
    }

    public List<Weather> getWeatherList(Location location, ObjectMapper mapper) {
        List<Weather> list = new ArrayList<>(settings.getDays());
        ForecastResponse forecastResponse = getForecastResponse(getResponse(location), configureMapper(mapper));
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        for (int i = 0; i < settings.getDays(); i++) {
            Weather weather = weatherBuilder.build(
                    forecastResponse.getDaily().getTime().get(i),
                    forecastResponse.getDaily().getSunrise().get(i),
                    forecastResponse.getDaily().getSunset().get(i),
                    forecastResponse.getDaily().getWeatherCode().get(i),
                    forecastResponse.getDaily().getPrecipitation().get(i),
                    forecastResponse.getDaily().getTemperature().get(i),
                    forecastResponse.getDaily().getWindspeed().get(i),
                    forecastResponse.getDailyUnits(),
                    "°C",
                    false);
            list.add(weather);
        }
        return list;
    }

    private ObjectMapper configureMapper(ObjectMapper mapper) {
        mapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
        return mapper;
    }
}
