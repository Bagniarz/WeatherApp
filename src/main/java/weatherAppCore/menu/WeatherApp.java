package weatherAppCore.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.dataRetrieval.Geocoding;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.exceptions.internalServerException.InternalServerException;
import weatherAppCore.exceptions.languageImportFileException.LanguageImportFileException;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;
import weatherAppCore.exceptions.wrongInputException.locationNotFoundException.LocationNotFoundException;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class WeatherApp {
    final Settings settings;
    final ObjectMapper mapper;
    final UserInput input;
    final Geocoding geocoding;
    final WeatherForecastProvider provider;
    Language language;

    public static WeatherApp initAppConfiguration() {
        SettingsFactory settingsFactory = new SettingsFactory();
        ObjectMapper mapper = new ObjectMapper();
        LanguageProvider languageProvider = new LanguageProvider("No language files found!");
        Settings settings = settingsFactory.createDefaultSettings();
        WeatherApp weatherApp;
        try {
            weatherApp = new WeatherApp(settings,
                    mapper,
                    UserInput.builder()
                            .excMess("Wrong Input")
                            .build(),
                    Geocoding.builder()
                            .client(HttpClient.newBuilder().build())
                            .excMessLocationNotFound("Location not found!")
                            .excMessInternalServerException("Geocoding server failure! Try again")
                            .build(),
                    new WeatherForecastProvider(settings),
                    languageProvider.importLanguage(LanguageSettings.ENGLISH, mapper));
        } catch (LanguageImportFileException e) {
            throw new RuntimeException();
        }
        return weatherApp;
    }

    private void print(List<String> list) {
        list.forEach(System.out::println);
    }

//    Rework this method to separate responsibility
    private void printResult(List<Weather> list) {
       for (Weather weather : list) {
           System.out.println(getTranslation(weather));
       }
    }

    private String getTranslation(Weather weather) {
        String endString = ". ";
        return language.getMap().get("result").get(0) +
                " {" +
                language.getMap().get("result").get(1) + weather.getTime() + endString +
                language.getMap().get("result").get(2) + weather.getSunrise() + endString +
                language.getMap().get("result").get(3) + weather.getSunset() + endString +
                language.getMap().get("result").get(4) + language.getWeatherStatus().get(weather.getWeatherCode()) + endString +
                language.getMap().get("result").get(5) + weather.getPrecipitation_probability_max() + weather.getUnitInfo().getPrecipitation() + endString +
                language.getMap().get("result").get(6) + weather.getTemperature_2m_max() + weather.getUnitInfo().getTemperature() + endString +
                language.getMap().get("result").get(7) +  weather.getWindSpeed_10_max() + weather.getUnitInfo().getWindSpeed() + endString +
                " }";
    }

    public void startAppMenu() {
        boolean endApp = false;
        print(language.getMap().get("welcome"));
        while(!endApp) {
            print(language.getMap().get("mainMenu"));
            try {
                input.askUserInt();
                switch (input.getInteger()) {
                    case 1 -> startWeatherForecasting();
                    case 2 -> changeSettingsMenu();
                    case 10 -> System.out.println(settings);
                    default -> endApp = true;
                }
            } catch (WrongInputException e) {
                input.clear();
            }
        }
    }

    public void startWeatherForecasting() {
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
       initWeatherForecastProvider(cityName, country);
    }

    public void initWeatherForecastProvider(String cityName, String country) {
        Coordinates coordinates;
        LocationFactory locationFactory = new LocationFactory();
        try {
            coordinates = geocoding.importCoordinates(cityName, country, (String) settings.getProp().get("apiKey"), mapper);
        } catch (LocationNotFoundException | InternalServerException exception) {
            return;
        }
        printResult(provider.getWeatherList(locationFactory.buildLocation(coordinates, cityName), mapper));
    }

    private void changeSettingsMenu() {
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
        LanguageProvider languageProvider = new LanguageProvider(language.getErrMessMap().get("LanguageImportFileException"));
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("language"));
            try {
                input.askUserInt();
                switch (input.getInteger()) {
                    case 1 -> {
                        settings.setLanguageSettings(LanguageSettings.ENGLISH);
                        try {
                            setLanguage(languageProvider.importLanguage(settings.getLanguageSettings(), mapper));
                        } catch (LanguageImportFileException e) {
                            throw new RuntimeException();
                        }
                        updateExcMess();
                    }
                    case 2 -> {
                        settings.setLanguageSettings(LanguageSettings.POLISH);
                        try {
                            setLanguage(languageProvider.importLanguage(settings.getLanguageSettings(), mapper));
                        } catch (LanguageImportFileException e) {
                            throw new RuntimeException();
                        }
                        updateExcMess();
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

    private void updateExcMess() {
        input.setExcMess(language.getErrMessMap().get("WrongInputException"));
        settings.setExcMess(language.getErrMessMap().get("DaysException"));
        geocoding.setExcMessLocationNotFound(language.getErrMessMap().get("LocationNotFoundException"));
        geocoding.setExcMessInternalServerException(language.getErrMessMap().get("InternalServerException"));
    }
}
