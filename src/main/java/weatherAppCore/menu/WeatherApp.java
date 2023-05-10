package weatherAppCore.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.dataRetrieval.Geocoding;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.exceptions.*;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;
import weatherAppCore.exceptions.wrongInputException.components.LocationNotFoundException;
import weatherAppCore.location.Location;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.location.savedLocations.FavouriteLocations;
import weatherAppCore.location.savedLocations.FavouriteLocationsProvider;
import weatherAppCore.location.savedLocations.FavouriteLocationsSaver;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.WeatherInfoSettings;
import weatherAppCore.settings.language.Language;
import weatherAppCore.settings.language.LanguageProvider;
import weatherAppCore.settings.language.LanguageSettings;
import weatherAppCore.userInput.UserInput;
import weatherAppCore.weather.Weather;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

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
    final PrintStream printOut;
    final PrintStream printErr;
    final FavouriteLocations locations;
    final FavouriteLocationsProvider locationsProvider;
    final FavouriteLocationsSaver saver;
    private static final Logger logger = LogManager.getLogger(WeatherApp.class);

// TODO Method that gives to System.out string to print
// TODO Refactor logger
// TODO Read about Hamcrest
// TODO Rework Tests

//  Printer with translator

    public void print(List<String> list) {
        list.forEach(printOut::println);
    }

    public void printResult(List<Weather> list) {
        for (Weather weather : list) {
            printOut.println(getTranslation(weather));
        }
    }

    public String getTranslation(Weather weather) {
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
        loadFavouriteLocations();
        boolean endApp = false;
        print(language.getMap().get("welcome"));
        while (!endApp) {
            print(language.getMap().get("mainMenu"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> initWeatherForecast();
                    case 2 -> manageFavouriteLocations();
                    case 3 -> changeSettingsMenu();
                    case 10 -> printOut.print(settings);
                    default -> {
                        endApp = true;
                        saveFavouriteLocations();
                    }
                }
            } catch (WrongInputException e){
            printErr.println(language.getErrMessMap().get("WrongInputException"));
            logger.debug(e);
            input.clear();
            }
        }
    }

//  WeatherForecasting

    public void initWeatherForecast() {
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("initWeatherForecast"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> startWeatherForecasting(false);
                    case 2 -> {
                        if (locations == null || locations.getMap() == null || locations.getMap().isEmpty()) {
                            printOut.println(language.getErrMessMap().get("EmptyMapException"));
                            return;
                        }
                        showFavouriteLocationsMap();
                        startWeatherForecasting(true);
                    }
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                printErr.println(language.getErrMessMap().get("WrongInputException"));
                logger.debug(e);
                input.clear();
                return;
            } catch (EmptyMapException e) {
                printErr.println(language.getErrMessMap().get("EmptyMapException"));
                logger.debug(e);
                input.clear();
            }
        }
    }

    public void startWeatherForecasting(boolean saved) {
        if (!saved) {
            print(language.getMap().get("startApp01"));
            String cityName;
            String country;
            try {
                input.askUserString(scanner);
                cityName = input.getString();
            } catch (WrongInputException e) {
                printErr.println(language.getErrMessMap().get("WrongInputException"));
                logger.debug(e);
                input.clear();
                return;
            }
            print(language.getMap().get("startApp02"));
            try {
                input.askUserString(scanner);
                country = input.getString();
            } catch (WrongInputException e) {
                printErr.println(language.getErrMessMap().get("WrongInputException"));
                logger.debug(e);
                input.clear();
                return;
            }
            initWeatherForecastProvider(cityName, country);
        } else {
            Location location = chooseLocationFromMap();
            if (location == null) {
                printErr.println(language.getErrMessMap().get("WrongLocationInput"));
                return;
            }
            initWeatherForecastProvider(location.getCityName(), "");
        }
    }

    public Location chooseLocationFromMap() {
        try {
            showFavouriteLocationsMap();
            input.askUserInt(scanner);
        } catch (EmptyMapException e) {
            printErr.println(language.getErrMessMap().get("EmptyMapException"));
            input.clear();
            logger.debug(e);
        } catch (WrongInputException e) {
            printErr.println(language.getErrMessMap().get("WrongInputException"));
            input.clear();
            logger.debug(e);
        }
        return locations.getMap().get(input.getInteger());
    }

    public void initWeatherForecastProvider(String cityName, String country) {
        Coordinates coordinates;
        LocationFactory locationFactory = new LocationFactory();
        try {
            coordinates = geocoding.importCoordinates(cityName, country, settings.getProp().get("apiKey").toString());
        } catch (LocationNotFoundException e) {
            printErr.println(language.getErrMessMap().get("LocationNotFoundException"));
            logger.debug(e);
            return;
        } catch (InternalAPIConnectionException e) {
            printErr.println(language.getErrMessMap().get("InternalAPIConnectionException"));
            logger.debug(e);
            return;
        }
        Location location = locationFactory.buildLocation(coordinates, cityName);
        List<Weather> result = provider.getWeatherList(location);
        printResult(result);
        askUserSaveLocation(location);
    }

    public void askUserSaveLocation(Location location) {
        printOut.println(location);
        print(language.getMap().get("addLocation"));
        try {
            input.askUserInt(scanner);
            if (input.getInteger() == 1) {
                try {
                    addLocationToMap(location);
                    print(language.getMap().get("savedSuccessful"));
                } catch (IOException e) {
                    printErr.println(language.getErrMessMap().get("IOException"));
                    logger.debug(e);
                }
            }
        } catch (WrongInputException e) {
            printErr.println(language.getErrMessMap().get("WrongInputException"));
            logger.debug(e);
            input.clear();
        } catch (MaximumCapacityException e) {
            printErr.println(language.getErrMessMap().get("MaximumCapacityException"));
            logger.debug(e);
            input.clear();
        } catch (DuplicateLocationException e) {
            printErr.println(language.getErrMessMap().get("DuplicateLocationException"));
            logger.debug(e);
            input.clear();
        }
    }

//  FavouriteLocation

    public void loadFavouriteLocations() {
        try {
            locations.setMap(locationsProvider.createMap
                    (new File("src/main/java/weatherAppCore/location/savedLocations/savedLocationsStorage/savedLocations.json")));
        } catch (IOException ignored) {
//         it's for ignoring empty file savedLocations.json
        }
    }

    public void addLocationToMap(Location location) throws MaximumCapacityException, DuplicateLocationException, IOException {
            locations.addLocation(location);
    }
    public void manageFavouriteLocations() {
        boolean endCycle = false;
        while (!endCycle) {
            print(language.getMap().get("favouriteLocations"));
            try {
                input.askUserInt(scanner);
                switch (input.getInteger()) {
                    case 1 -> showFavouriteLocationsMap();
                    case 2 -> removeLocationFromMap();
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                printErr.println(language.getErrMessMap().get("WrongInputException"));
                logger.debug(e);
                input.clear();
            } catch (EmptyMapException e) {
                printErr.println(language.getErrMessMap().get("EmptyMapException"));
                logger.debug(e);
                input.clear();
            }
        }
    }

    public void showFavouriteLocationsMap() throws EmptyMapException {
        if (locations == null || locations.getMap() == null || locations.getMap().isEmpty()) throw new EmptyMapException();
        printOut.println(locations.getMap());
    }

    public void removeLocationFromMap() {
        printOut.println(language.getMap().get("removeLocation"));
        try {
            showFavouriteLocationsMap();
            input.askUserInt(scanner);
            locations.removeLocation(input.getInteger());
        } catch (EmptyMapException e) {
            printErr.println(language.getErrMessMap().get("EmptyMapException"));
            logger.debug(e);
            input.clear();
        } catch (WrongInputException e) {
            printErr.println(language.getErrMessMap().get("WrongInputException"));
            logger.debug(e);
            input.clear();
        } catch (IOException e) {
            printErr.println(language.getErrMessMap().get("IOException"));
            logger.debug(e);
        }
    }

    public void saveFavouriteLocations() {
        try {
            saver.createJSONFile(locations.getMap(),
                    new File("src/main/java/weatherAppCore/location/savedLocations/savedLocationsStorage/savedLocations.json"));
        } catch (IOException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

//  Settings

    public void changeSettingsMenu() {
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
                printOut.println(language.getErrMessMap().get("WrongInputException"));
                logger.debug(e);
                input.clear();
            }
        }
    }

    public void changeLanguage() {
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
                            printErr.println(language.getErrMessMap().get("LanguageImportFileException"));
                            saveFavouriteLocations();
                            logger.fatal(e);
                            throw new RuntimeException(e);
                        }
                    }
                    case 2 -> {
                        settings.setLanguageSettings(LanguageSettings.POLISH);
                        try {
                            setLanguage(languageProvider.importLanguage(settings.getLanguageSettings()));
                        } catch (LanguageImportFileException e) {
                            printOut.println(language.getErrMessMap().get("LanguageImportFileException"));
                            saveFavouriteLocations();
                            logger.fatal(e);
                            throw new RuntimeException(e);
                        }
                    }
                    default -> endCycle = true;
                }
            } catch (WrongInputException e) {
                printOut.println(language.getErrMessMap().get("WrongInputException"));
                logger.info(language.getErrMessMap().get("WrongInputException"));
                input.clear();
            }
            print(language.getMap().get("successfulChange"));
        }
    }

    public void changeDays() {
        print(language.getMap().get("daysChange"));
        try {
            input.askUserInt(scanner);
            settings.setDays(input.getInteger());
        } catch (WrongInputException e) {
            printOut.println(language.getErrMessMap().get("WrongInputException"));
            logger.debug(e);
            input.clear();
        }
        print(language.getMap().get("successfulChange"));
    }

    public void changeScale() {
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
                printOut.println(language.getErrMessMap().get("WrongInputException"));
                logger.debug(e);
                input.clear();
            }
            print(language.getMap().get("successfulChange"));
        }
    }
}
