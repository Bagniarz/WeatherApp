package weatherAppCore.exceptions;

public class WrongInputException extends Exception{
    public WrongInputException() {
        System.err.println("Wrong Input");
    }
}
