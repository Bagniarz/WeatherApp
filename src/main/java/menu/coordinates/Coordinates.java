package menu.coordinates;

public record Coordinates(double latitude, double longitude) {
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
