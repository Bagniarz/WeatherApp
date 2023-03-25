package menu.weather.components;

import menu.settings.WeatherInfoSettings;

public class Temperature {
    private int temperature;
    private WeatherInfoSettings weatherInfoSettings;

    public Temperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void changeScale() {
        switch (this.weatherInfoSettings) {
            case FAHRENHEIT -> setTemperature((int) (temperature * 1.8) + 32);
            case CELSIUS -> setTemperature((int) (temperature / 1.8) - 32);
        }
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temperature=" + temperature +
                " " + weatherInfoSettings +
                '}';
    }
}
