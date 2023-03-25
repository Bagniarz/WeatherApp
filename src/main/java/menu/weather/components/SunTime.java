package menu.weather.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SunTime {
    private Date sunRise, sunSet;

    public SunTime(String sunRise, String sunSet) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        try {
            this.sunRise = simpleDateFormat.parse(sunRise);
            this.sunSet = simpleDateFormat.parse(sunSet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getSunRise() {
        return sunRise;
    }

    public Date getSunSet() {
        return sunSet;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        return "SunTime{" +
                "sunRise=" + simpleDateFormat.format(sunRise) +
                ", sunSet=" + simpleDateFormat.format(sunSet) +
                '}';
    }
}
