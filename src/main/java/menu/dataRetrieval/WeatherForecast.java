package menu.dataRetrieval;

import menu.location.Location;
import menu.settings.Settings;
import menu.weather.Weather;
import menu.weather.builder.WeatherBuilder;
import menu.weather.components.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    private Settings settings;
    private Location location;

    public WeatherForecast(Settings settings, Location location) {
        this.settings = settings;
        this.location = location;
    }

    public List<Weather> getWeatherList() {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://api.open-meteo.com/v1/forecast?latitude="
                            + this.location.coordinates().latitude() + "&longitude="
                            + this.location.coordinates().longitude()
                            + "&daily=temperature_2m_max,precipitation_probability_max,weathercode,sunrise,sunset,windspeed_10m_max" +
                            "&forecast_days=" + this.settings.getDays() + "&timezone=auto"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonObject = new JSONObject(response.body());
            jsonObject = jsonObject.getJSONObject("daily");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return createWeatherList(jsonObject);
    }

    public List<Weather> createWeatherList(JSONObject input) {
        WeatherBuilder builder = new WeatherBuilder();
        List<Weather> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < settings.getDays(); i++) {
            try {
                builder.setDate(format.parse(input.getJSONArray("time").getString(i)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String temp02 = input.getJSONArray("sunrise").getString(i);
            String temp03 = input.getJSONArray("sunset").getString(i);
            builder.setLocation(this.location);
            builder.setTemperature(new Temperature(input.getJSONArray("temperature_2m_max").getInt(i)));
            builder.setPrecipitation(new Precipitation(input.getJSONArray("precipitation_probability_max").getInt(i)));
            builder.setWeatherCode(new WeatherCode(input.getJSONArray("weathercode").getInt(i)));
            builder.setSunTime(new SunTime(temp02.substring(temp02.indexOf("T") + 1), temp03.substring(temp03.indexOf("T") + 1)));
            builder.setWindSpeed(new WindSpeed(input.getJSONArray("windspeed_10m_max").getFloat(i)));
            list.add(builder.getResult());
        }
        return list;
    }
}
