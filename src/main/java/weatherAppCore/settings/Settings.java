package weatherAppCore.settings;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.wrongInputException.daysException.DaysException;
import weatherAppCore.settings.language.LanguageSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class Settings {
    String excMess;
    WeatherInfoSettings weatherInfoSettings;
    LanguageSettings languageSettings;
    int days;
    Properties prop;

    public Settings(WeatherInfoSettings weatherInfoSettings, LanguageSettings languageSettings, int days, String excMess) {
        this.weatherInfoSettings = weatherInfoSettings;
        this.languageSettings = languageSettings;
        this.excMess = excMess;
        this.days = days;
        this.prop = loadConfig();
    }

    public void setDays(int days) throws DaysException {
        if (days < 1 || days > 16) {
            throw new DaysException(excMess);
        }
        this.days = days;
    }

//    TODO Add exception to missing API Key
    public Properties loadConfig() {
        String configFilePath = "src/main/resources/WeatherApp.properties";
        Properties properties = new Properties();
        try(FileInputStream inputStream = new FileInputStream(configFilePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return properties;
    }
}
