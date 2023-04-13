import weatherAppCore.menu.WeatherApp;

public class Main {
    public static void main(String[] args) {
        WeatherApp weatherApp = WeatherApp.initAppConfiguration();
        weatherApp.startAppMenu();
    }
}
