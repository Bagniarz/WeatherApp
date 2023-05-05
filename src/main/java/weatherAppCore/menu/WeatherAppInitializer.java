package weatherAppCore.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weatherAppCore.dataRetrieval.Geocoding;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.exceptions.LanguageImportFileException;
import weatherAppCore.location.savedLocations.FavouriteLocations;
import weatherAppCore.location.savedLocations.FavouriteLocationsProvider;
import weatherAppCore.location.savedLocations.FavouriteLocationsSaver;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.SettingsFactory;
import weatherAppCore.settings.language.LanguageProvider;
import weatherAppCore.settings.language.LanguageSettings;
import weatherAppCore.userInput.UserInput;

import java.io.PrintStream;
import java.net.http.HttpClient;
import java.util.LinkedHashMap;

public class WeatherAppInitializer {

    private static final Logger logger = LogManager.getLogger(WeatherAppInitializer.class);

    public static WeatherApp initAppConfiguration() {
        SettingsFactory settingsFactory = new SettingsFactory();
        ObjectMapper mapper = new ObjectMapper();
        LanguageProvider languageProvider = new LanguageProvider(mapper);
        Settings settings = settingsFactory.createDefaultSettings();
        WeatherApp weatherApp;
        FavouriteLocationsProvider provider = new FavouriteLocationsProvider(mapper);
        try {
            weatherApp = new WeatherApp(settings,
                    UserInput.builder()
                            .build(),
                    Geocoding.builder()
                            .client(HttpClient.newBuilder().build())
                            .mapper(mapper)
                            .build(),
                    new WeatherForecastProvider(settings, mapper),
                    languageProvider,
                    languageProvider.importLanguage(LanguageSettings.ENGLISH),
                    new PrintStream(System.out),
                    new PrintStream(System.err),
                    new FavouriteLocations(new LinkedHashMap<>()),
                    provider,
                    new FavouriteLocationsSaver(mapper));
        } catch (LanguageImportFileException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
        return weatherApp;
    }
}
