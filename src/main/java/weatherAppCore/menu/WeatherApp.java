package weatherAppCore.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.dataRetrieval.Geocoding;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.exceptions.InternalAPIConnectionException;
import weatherAppCore.exceptions.LanguageImportFileException;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;
import weatherAppCore.exceptions.wrongInputException.components.LocationNotFoundException;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.WeatherInfoSettings;
import weatherAppCore.settings.language.Language;
import weatherAppCore.settings.language.LanguageProvider;
import weatherAppCore.settings.language.LanguageSettings;
import weatherAppCore.userInput.UserInput;
import weatherAppCore.weather.Weather;

import java.util.List;
import java.util.Scanner;

// TODO Rework exceptions using log4j

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class WeatherApp {
    final Settings settings;
    final UserInput input;
    final Geocoding geocoding;
    final WeatherForecastProvider provider;
    final LanguageProvider languageProvider;
    Language language;
    final Scanner scanner = new Scanner(System.in);

//  Printer with translator

    private void print(List<String> list) {
        list.forEach(System.out::println);
    }

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
                language.getMap().get("result").get(7) + weather.getWindSpeed_10_max() + weather.getUnitInfo().getWindSpeed() + endString +
                " }";
    }

//  WeatherApp Merging all core methods

    public void startAppMenu() {
        boolean endApp = false;
        print(language.getMap().get("welcome"));
        while (!endApp) {
            print(language.getMap().get("mainMenu"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> startWeatherForecasting();
                    case 2 -> changeSettingsMenu();
                    case 10 -> System.out.println(settings);
                    default -> endApp = true;
                }
            } catch (WrongInputException e) {
                System.err.println(language.getErrMessMap().get("WrongInputException"));
                input.clear();
            }
        }
    }

//  WeatherForecasting

    public void startWeatherForecasting() {
        print(language.getMap().get("startApp01"));
        String cityName;
        String country;
        try {
            input.askUserString(scanner);
            cityName = input.getString();
        } catch (WrongInputException e) {
            System.err.println(language.getErrMessMap().get("WrongInputException"));
            input.clear();
            return;
        }
        print(language.getMap().get("startApp02"));
        try {
            input.askUserString(scanner);
            country = input.getString();
        } catch (WrongInputException e) {
            System.err.println(language.getErrMessMap().get("WrongInputException"));
            input.clear();
            return;
        }
        initWeatherForecastProvider(cityName, country);
    }

    public void initWeatherForecastProvider(String cityName, String country) {
        Coordinates coordinates;
        LocationFactory locationFactory = new LocationFactory();
        try {
            coordinates = geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString());
        } catch (LocationNotFoundException exception) {
            System.err.println(language.getErrMessMap().get("LocationNotFoundException"));
            return;
        } catch (InternalAPIConnectionException e) {
            System.err.println(language.getErrMessMap().get("InternalAPIConnectionException"));
            return;
        }
        printResult(provider.getWeatherList(locationFactory.buildLocation(coordinates, cityName)));
    }

//  Settings

    private void changeSettingsMenu() {
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("settings"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> changeLanguage();
                    case 2 -> changeDays();
                    case 3 -> changeScale();
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                System.err.println(language.getErrMessMap().get("WrongInputException"));
                input.clear();
            }
        }
    }

    private void changeLanguage() {
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("language"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> {
                        settings.setLanguageSettings(LanguageSettings.ENGLISH);
                        try {
                            setLanguage(languageProvider.importLanguage(settings.getLanguageSettings()));
                        } catch (LanguageImportFileException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 2 -> {
                        settings.setLanguageSettings(LanguageSettings.POLISH);
                        try {
                            setLanguage(languageProvider.importLanguage(settings.getLanguageSettings()));
                        } catch (LanguageImportFileException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                System.err.println(language.getErrMessMap().get("WrongInputException"));
                input.clear();
            }
            print(language.getMap().get("successfulChange"));
        }
    }

    private void changeDays() {
        print(language.getMap().get("daysChange"));
        try {
            input.askUserInt(scanner);
            settings.setDays(input.getInteger());
        } catch (WrongInputException e) {
            System.err.println(language.getErrMessMap().get("WrongInputException"));
            input.clear();
        }
        print(language.getMap().get("successfulChange"));
    }

    private void changeScale() {
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("changeScale"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> settings.setWeatherInfoSettings(WeatherInfoSettings.CELSIUS);
                    case 2 -> settings.setWeatherInfoSettings(WeatherInfoSettings.FAHRENHEIT);
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                System.err.println(language.getErrMessMap().get("WrongInputException"));
                input.clear();
            }
            print(language.getMap().get("successfulChange"));
        }
    }
}
