package weatherAppCore.settings;

import lombok.Data;
import weatherAppCore.settings.language.Language;

public @Data class Settings {
    private WeatherInfoSettings weatherInfoSettings;
    private Language language;
    private int days;

    public Settings(WeatherInfoSettings weatherInfoSettings, Language language, int days) {
        this.weatherInfoSettings = weatherInfoSettings;
        this.language = language;
        if (days < 1 || days > 16) this.days = 1;
        else this.days = days;
    }
}
