package weatherAppCore.coordinates;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CoordinatesFactory {
    public Coordinates buildCoordinates(double x, double y) {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        try {
            x = Double.parseDouble(nf.parse(df.format(x)).toString());
            y = Double.parseDouble(nf.parse(df.format(y)).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Coordinates(x, y);
    }
}
