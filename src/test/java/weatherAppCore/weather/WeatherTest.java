package weatherAppCore.weather;

import org.junit.jupiter.api.Test;
import weatherAppCore.dataRetrieval.forecastResponse.components.UnitInfo;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.SettingsFactory;
import weatherAppCore.settings.WeatherInfoSettings;
import weatherAppCore.settings.language.LanguageSettings;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherTest {

    private final SettingsFactory settingsFactory = new SettingsFactory();
    private final Settings settingsCelsius = settingsFactory.createDefaultSettings();
    private final Settings settingsFahrenheit = new Settings(WeatherInfoSettings.FAHRENHEIT, LanguageSettings.ENGLISH, 1);
    private final WeatherBuilder weatherBuilder = new WeatherBuilder();



    @Test
    void changeScaleToCelsius() {
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -40, 0,
                UnitInfo.builder().temperature("°F").build());
        weather.changeScale(settingsCelsius.getWeatherInfoSettings());
        assertTrue(-40 == weather.getTemperature_2m_max() && "°C".equals(weather.getUnitInfo().getTemperature()));
    }

    @Test
    void changeScaleToFahrenheit() {
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -40, 0,
                UnitInfo.builder().temperature("°C").build());
        weather.changeScale(settingsFahrenheit.getWeatherInfoSettings());
        assertTrue(-40 == weather.getTemperature_2m_max() && "°F".equals(weather.getUnitInfo().getTemperature()));
    }

    @Test
    void changeScaleToFahrenheitTemperatureTestPositiveNumber() {
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, 40, 0,
                UnitInfo.builder().temperature("°C").build());
        weather.changeScale(settingsFahrenheit.getWeatherInfoSettings());
        assertTrue(104 == weather.getTemperature_2m_max() && "°F".equals(weather.getUnitInfo().getTemperature()));
    }

    @Test
    void changeScaleToFahrenheitTemperatureTestNegativeNumber() {
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -25, 0,
                UnitInfo.builder().temperature("°C").build());
        weather.changeScale(settingsFahrenheit.getWeatherInfoSettings());
        assertTrue(-13 == weather.getTemperature_2m_max() && "°F".equals(weather.getUnitInfo().getTemperature()));
    }

    @Test
    void changeScaleToCelsiusTemperatureTestPositiveNumber() {
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, 32, 0,
                UnitInfo.builder().temperature("°F").build());
        weather.changeScale(settingsCelsius.getWeatherInfoSettings());
        assertTrue(0 == weather.getTemperature_2m_max() && "°C".equals(weather.getUnitInfo().getTemperature()));
    }

    @Test
    void changeScaleToCelsiusTemperatureTestNegativeNumber() {
        Weather weather = weatherBuilder.build("TEST","TEST", "TEST",
                0, 0, -25, 0,
                UnitInfo.builder().temperature("°F").build());
        weather.changeScale(settingsCelsius.getWeatherInfoSettings());
        assertTrue(-31 == weather.getTemperature_2m_max() && "°C".equals(weather.getUnitInfo().getTemperature()));
    }
}