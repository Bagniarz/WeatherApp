import weatherAppCore.menu.Menu;
import weatherAppCore.coordinates.Coordinates;
import weatherAppCore.dataRetrieval.WeatherForecastProvider;
import weatherAppCore.location.LocationFactory;
import weatherAppCore.menu.MenuPrint;
import weatherAppCore.settings.SettingsFactory;
import weatherAppCore.userInput.UserInput;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        SettingsFactory factory = new SettingsFactory();
        Menu menu = new Menu(factory.createDefaultSettings(), new UserInput(), new MenuPrint());
        menu.startApp();


        HttpClient client = HttpClient.newBuilder().build();
        WeatherForecastProvider provider = new WeatherForecastProvider(client, factory.createDefaultSettings());
        LocationFactory locationFactory = new LocationFactory();
        provider.getWeatherList(locationFactory.buildLocation(new Coordinates(52, 21), "Warsaw"));
    }
}
