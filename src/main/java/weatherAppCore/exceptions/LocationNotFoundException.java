package weatherAppCore.exceptions;

public class LocationNotFoundException extends Exception {
    public LocationNotFoundException() {
        System.err.println("Location doesn't exist. Check your spelling!");
    }
}
