package weatherAppCore.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import weatherAppCore.dataRetrieval.Geocoding;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.exceptions.LanguageImportFileException;
import weatherAppCore.settings.Settings;
import weatherAppCore.settings.SettingsFactory;
import weatherAppCore.settings.language.LanguageProvider;
import weatherAppCore.settings.language.LanguageSettings;
import weatherAppCore.userInput.UserInput;

import java.io.PrintStream;
import java.net.http.HttpClient;

public class WeatherAppInitializer {

    public static WeatherApp initAppConfiguration() {
        SettingsFactory settingsFactory = new SettingsFactory();
        ObjectMapper mapper = new ObjectMapper();
        LanguageProvider languageProvider = new LanguageProvider(mapper);
        Settings settings = settingsFactory.createDefaultSettings();
        WeatherApp weatherApp;
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
                    new PrintStream(System.err));
        } catch (LanguageImportFileException e) {
            throw new RuntimeException();
        }
        return weatherApp;
    }
}
