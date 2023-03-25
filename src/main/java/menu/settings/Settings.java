package menu.settings;

import menu.settings.language.Language;

public class Settings {
    private static Settings instance;
    private WeatherInfoSettings weatherInfoSettings;
    private Language language;
    private int days;

    private Settings(WeatherInfoSettings weatherInfoSettings, Language language ,int days) {
        this.weatherInfoSettings = weatherInfoSettings;
        this.language = language;
        this.days = days;
        if (this.days < 1 || this.days > 16) {
            this.days = 1;
        }
    }

    public static Settings getSettings(WeatherInfoSettings weatherInfoSettings, Language language, int days) {
        if (instance == null) {
            instance = new Settings(weatherInfoSettings, language, days);
        }
        return instance;
    }

    public WeatherInfoSettings getWeatherInfoSettings() {
        return weatherInfoSettings;
    }

    public void setWeatherInfoSettings(WeatherInfoSettings weatherInfoSettings) {
        this.weatherInfoSettings = weatherInfoSettings;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
