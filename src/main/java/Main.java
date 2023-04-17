import weatherAppCore.menu.WeatherApp;
import weatherAppCore.menu.WeatherAppInitializer;

public class Main {
    public static void main(String[] args) {
        WeatherApp weatherApp = WeatherAppInitializer.initAppConfiguration();
        weatherApp.startAppMenu();
    }
}
