package weatherAppCore.dataRetrieval;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GeocodingTest {

    @Test
    void importCoordinatesData01() {
        String[] input = new String[] {"London", "England"};
        double[] test01 = new double[] {51.5073219, -0.1276474};
        assertArrayEquals(test01, Geocoding.importCoordinatesData(input));
    }

    @Test
    void importCoordinatesData02() {
        String[] input = new String[] {"Warsaw", ""};
        double[] test02 = new double[] {52.2319581, 21.0067249};
        assertArrayEquals(test02, Geocoding.importCoordinatesData(input));
    }
}