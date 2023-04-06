package weatherAppCore.settings;

import lombok.Data;
import weatherAppCore.settings.language.LanguageSettings;

public @Data class Settings {
    private WeatherInfoSettings weatherInfoSettings;
    private LanguageSettings languageSettings;
    private int days;

    public Settings(WeatherInfoSettings weatherInfoSettings, LanguageSettings languageSettings, int days) {
        this.weatherInfoSettings = weatherInfoSettings;
        this.languageSettings = languageSettings;
        if (days < 1 || days > 16) this.days = 1;
        else this.days = days;
    }
}
