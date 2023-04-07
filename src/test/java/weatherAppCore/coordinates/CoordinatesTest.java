package weatherAppCore.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CoordinatesTest {

    @Test
    void createCoordinates_FactoryMethod() {
        CoordinatesFactory factory = new CoordinatesFactory();
        Coordinates coordinates = factory.buildCoordinates(43.0, 35.50);
        assertTrue(coordinates.latitude() == 43.0 && coordinates.longitude() == 35.50);
    }

    @Test
    void createCoordinates_NegativeNumbers_FactoryMethod() {
        CoordinatesFactory factory = new CoordinatesFactory();
        Coordinates coordinates = factory.buildCoordinates(-23.0, -89.58);
        assertTrue(coordinates.latitude() == -23.0 && coordinates.longitude() == -89.58);
    }

    @Test
    void truncate_PositiveNumbers() {
        CoordinatesFactory factory = new CoordinatesFactory();
        Coordinates coordinates = factory.buildCoordinates(51.519999, 23.534);
        assertTrue(coordinates.latitude() == 51.52 && coordinates.longitude() == 23.534);
    }

    @Test
    void truncate_NegativeNumbers() {
        CoordinatesFactory factory = new CoordinatesFactory();
        Coordinates coordinates = factory.buildCoordinates(-32.78902, -0.12344);
        assertTrue(coordinates.latitude() == -32.789 && coordinates.longitude() == -0.123);
    }
}