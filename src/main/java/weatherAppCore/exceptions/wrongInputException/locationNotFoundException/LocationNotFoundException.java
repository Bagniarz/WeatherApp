package weatherAppCore.exceptions.wrongInputException.locationNotFoundException;

import weatherAppCore.exceptions.wrongInputException.WrongInputException;

public class LocationNotFoundException extends WrongInputException {
    public LocationNotFoundException(String message) {
        super(message);
    }
}
