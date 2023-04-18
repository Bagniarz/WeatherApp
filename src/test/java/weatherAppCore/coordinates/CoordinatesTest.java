package weatherAppCore.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CoordinatesTest {

    private final CoordinatesFactory factory = new CoordinatesFactory();

    @Test
    void createCoordinates_FactoryMethod() {
        Coordinates coordinates = factory.buildCoordinates(43.0, 35.50);
        assertTrue(coordinates.getLatitude() == 43.0 && coordinates.getLongitude() == 35.50);
    }

    @Test
    void createCoordinates_NegativeNumbers_FactoryMethod() {
        Coordinates coordinates = factory.buildCoordinates(-23.0, -89.58);
        assertTrue(coordinates.getLatitude() == -23.0 && coordinates.getLongitude() == -89.58);
    }

    @Test
    void truncate_PositiveNumbers() {
        Coordinates coordinates = factory.buildCoordinates(51.519999, 23.534);
        assertTrue(coordinates.getLatitude() == 51.52 && coordinates.getLongitude() == 23.534);
    }

    @Test
    void truncate_NegativeNumbers() {
        Coordinates coordinates = factory.buildCoordinates(-32.78902, -0.12344);
        assertTrue(coordinates.getLatitude() == -32.789 && coordinates.getLongitude() == -0.123);
    }
}