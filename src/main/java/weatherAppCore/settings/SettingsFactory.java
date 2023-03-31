package weatherAppCore.settings;

import weatherAppCore.settings.language.Language;

public class SettingsFactory {
    public Settings createDefaultSettings() {
        return new Settings(WeatherInfoSettings.CELSIUS, Language.ENGLISH, 1);
    }
}
