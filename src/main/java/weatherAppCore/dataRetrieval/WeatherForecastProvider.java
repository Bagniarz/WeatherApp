package weatherAppCore.dataRetrieval;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import weatherAppCore.dataRetrieval.forecastResponse.ForecastResponse;
import weatherAppCore.location.Location;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.WeatherInfoSettings;
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
    ObjectMapper mapper;

    public WeatherForecastProvider(Settings settings, ObjectMapper mapper) {
        this(HttpClient.newHttpClient(), settings, mapper);
    }

    public WeatherForecastProvider(HttpClient client, Settings settings, ObjectMapper mapper) {
        this.client = client;
        this.settings = settings;
        this.mapper = mapper;
    }

    private ForecastResponse getForecastResponse(HttpResponse<String> response) {
        ForecastResponse forecastResponse;
        configureMapper();
        try {
            forecastResponse = mapper.readValue(response.body(), ForecastResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return forecastResponse;
    }

    private HttpResponse<String> getResponse(Location location) {
        HttpResponse<String> response;
        try {
            response = client.send(createRequest(location), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private HttpRequest createRequest(Location location) {
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
            throw new RuntimeException(e);
        }
        return request;
    }

    public List<Weather> getWeatherList(Location location) {
        List<Weather> list = new ArrayList<>(settings.getDays());
        ForecastResponse forecastResponse = getForecastResponse(getResponse(location));
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
                    forecastResponse.getDailyUnits());
            if (settings.getWeatherInfoSettings() == WeatherInfoSettings.FAHRENHEIT) weather.changeScale(settings.getWeatherInfoSettings());
            list.add(weather);
        }
        return list;
    }

    private void configureMapper() {
        mapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
    }
}
