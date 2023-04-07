package weatherAppCore.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherTest {

    @Test
    void changeScaleToCelsius() {
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -40, 0,
                null, "°F", true);
        weather.changeScale();
        assertTrue(-40 == weather.temperature_2m_max && "°C".equals(weather.scale));
    }

    @Test
    void changeScaleToFahrenheit() {
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -40, 0,
                null, "°C", false);
        weather.changeScale();
        assertTrue(-40 == weather.temperature_2m_max && "°F".equals(weather.scale));
    }

    @Test
    void changeScaleToFahrenheitTemperatureTestPositiveNumber() {
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, 40, 0,
                null, "°C", false);
        weather.changeScale();
        assertTrue(104 == weather.temperature_2m_max && "°F".equals(weather.scale));
    }

    @Test
    void changeScaleToFahrenheitTemperatureTestNegativeNumber() {
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -25, 0,
                null, "°C", false);
        weather.changeScale();
        assertTrue(-13 == weather.temperature_2m_max && "°F".equals(weather.scale));
    }

    @Test
    void changeScaleToCelsiusTemperatureTestPositiveNumber() {
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, 32, 0,
                null, "°C", true);
        weather.changeScale();
        assertTrue(0 == weather.temperature_2m_max && "°C".equals(weather.scale));
    }

    @Test
    void changeScaleToCelsiusTemperatureTestNegativeNumber() {
        WeatherBuilder weatherBuilder = new WeatherBuilder();
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -25, 0,
                null, "°C", true);
        weather.changeScale();
        assertTrue(-31 == weather.temperature_2m_max && "°C".equals(weather.scale));
    }
}