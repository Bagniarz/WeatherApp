package menu.location;

import menu.coordinates.Coordinates;

import java.util.Objects;

public record Location(Coordinates coordinates, String cityName) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(cityName, location.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName);
    }
}
