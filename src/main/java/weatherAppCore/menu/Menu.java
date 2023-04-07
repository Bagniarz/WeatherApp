package weatherAppCore.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.dataRetrieval.Geocoding;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.exceptions.LocationNotFoundException;
import weatherAppCore.exceptions.WrongInputException;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.SettingsFactory;
import weatherAppCore.settings.language.Language;
import weatherAppCore.settings.language.LanguageProvider;
import weatherAppCore.settings.language.LanguageSettings;
import weatherAppCore.userInput.UserInput;
import weatherAppCore.weather.Weather;

import java.net.http.HttpClient;
import java.util.List;

@Data
@AllArgsConstructor
public class Menu {
    final Settings settings;
    final UserInput input;
    final Geocoding geocoding;
    final WeatherForecastProvider provider;
    Language language;

    public static Menu initConfiguration() {
        SettingsFactory settingsFactory = new SettingsFactory();
        LanguageProvider languageProvider = new LanguageProvider();
        Settings settings = settingsFactory.createDefaultSettings();
        return new Menu(settings,
                UserInput.builder().build(),
                Geocoding.builder().client(HttpClient.newBuilder().build()).build(),
                new WeatherForecastProvider(settings),
                languageProvider.importLanguage(LanguageSettings.ENGLISH));
    }

    private void print(List<String> list) {
        list.forEach(System.out::println);
    }

    private void printResult(List<Weather> list) {
       for (Weather weather : list) {
           System.out.println( "Weather{" +
                   "time='" + weather.getTime() + '\'' +
                   ", sunrise= '" + weather.getSunrise() + '\'' +
                   ", sunset= '" + weather.getSunset() + '\'' +
                   ", weatherStatus= " + language.getWeatherStatus().get(weather.getWeatherCode()) +
                   ", precipitation_probability_max= " + weather.getPrecipitation_probability_max() + weather.getUnitInfo().getPrecipitation_probability_max() +
                   ", temperature_2m_max= " + weather.getTemperature_2m_max() + weather.getUnitInfo().getTemperature_2m_max() +
                   ", windSpeed_10_max =" + weather.getWindSpeed_10_max() + weather.getUnitInfo().getWindspeed_10m_max() +
                   '}');
       }
    }

    public void initApp() {
        boolean endApp = false;
        print(language.getMap().get("welcome"));
        while(!endApp) {
            print(language.getMap().get("mainMenu"));
            try {
                input.askUserInt();
                switch (input.getInteger()) {
                    case 1 -> startApp();
                    case 2 -> startSettings();
                    case 10 -> System.out.println(settings);
                    default -> endApp = true;
                }
            } catch (WrongInputException e) {
                input.clear();
            }
        }
    }

    public void startApp() {
       print(language.getMap().get("startApp01"));
       String cityName;
       String country;
       try {
           input.askUserString();
           cityName = input.getString();
       } catch (WrongInputException e) {
           input.clear();
           return;
       }
       print(language.getMap().get("startApp02"));
       try {
           input.askUserString();
           country = input.getString();
       } catch (WrongInputException e) {
           input.clear();
           return;
       }
       initWeatherForecast(cityName, country);
    }

    public void initWeatherForecast(String cityName, String country) {
        Coordinates coordinates;
        LocationFactory locationFactory = new LocationFactory();
        try {
            coordinates = geocoding.importCoordinates(cityName, country);
        } catch (LocationNotFoundException e) {
            return;
        }
        printResult(provider.getWeatherList(locationFactory.buildLocation(coordinates, cityName)));
    }

    private void startSettings() {
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("settings"));
            try {
                input.askUserInt();
                switch (input.getInteger()) {
                    case 1 -> changeLanguage();
                    case 2 -> changeDays();
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                input.clear();
            }
        }
    }

    private void changeLanguage() {
        LanguageProvider languageProvider = new LanguageProvider();
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("language"));
            try {
                input.askUserInt();
                switch (input.getInteger()) {
                    case 1 -> {
                        settings.setLanguageSettings(LanguageSettings.ENGLISH);
                        setLanguage(languageProvider.importLanguage(settings.getLanguageSettings()));
                    }
                    case 2 -> {
                        settings.setLanguageSettings(LanguageSettings.POLISH);
                        setLanguage(languageProvider.importLanguage(settings.getLanguageSettings()));
                    }
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                input.clear();
            }
            print(language.getMap().get("successfulChange"));
        }
    }

    private void changeDays() {
        print(language.getMap().get("daysChange"));
        try {
            input.askUserInt();
            settings.setDays(input.getInteger());
        } catch (WrongInputException e) {
            input.clear();
        }
        print(language.getMap().get("successfulChange"));
    }
}
