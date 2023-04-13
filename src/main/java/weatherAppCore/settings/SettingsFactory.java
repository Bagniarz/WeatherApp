package weatherAppCore.settings;

import weatherAppCore.settings.language.LanguageSettings;

public class SettingsFactory {
    public Settings createDefaultSettings() {
        return new Settings(WeatherInfoSettings.CELSIUS, LanguageSettings.ENGLISH, 1, "Days cannot be lower than 1 and higher than 16!");
    }
}
