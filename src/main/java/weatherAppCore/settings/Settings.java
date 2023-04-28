package weatherAppCore.settings;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.wrongInputException.components.DaysException;
import weatherAppCore.settings.language.LanguageSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Settings {
    WeatherInfoSettings weatherInfoSettings;
    LanguageSettings languageSettings;
    int days;
    Properties prop;

    public Settings(WeatherInfoSettings weatherInfoSettings, LanguageSettings languageSettings, int days) {
        this.weatherInfoSettings = weatherInfoSettings;
        this.languageSettings = languageSettings;
        this.days = days;
        this.prop = loadConfig();
    }

    public void setDays(int days) throws DaysException {
        if (days < 1 || days > 16) {
            throw new DaysException();
        }
        this.days = days;
    }

//    TODO Add exception to missing API Key
    public Properties loadConfig() {
        String configFilePath = "src/main/java/weatherAppCore/settings/apiStorage/WeatherApp.properties";
        Properties properties = new Properties();
        try(FileInputStream inputStream = new FileInputStream(configFilePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
