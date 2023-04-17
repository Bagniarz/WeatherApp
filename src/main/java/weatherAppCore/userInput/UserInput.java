package weatherAppCore.userInput;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
//TODO Create more testable methods or change user input logic to make it more testable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserInput {
    String string;
    int integer;
    Scanner scanner;
    private void initScanner() {
        this.scanner = new Scanner(new InputStreamReader(System.in));
    }

    public void askUserInt(InputStream stream) throws WrongInputException {
        initScanner();
        String result = stream.toString();
        if (result.length() > 2 || !isLetterInNumber(result)) {
            scanner.close();
            throw new WrongInputException();
        }
        setInteger(Integer.parseInt(result));
        scanner.close();
    }

    public void askUserInt() throws WrongInputException {
        initScanner();
        String result = scanner.nextLine();
        if (result.length() > 2 || !isLetterInNumber(result)) {
            scanner.close();
            throw new WrongInputException();
        }
        setInteger(Integer.parseInt(result));
        scanner.close();
    }

    public void askUserString(InputStream stream) throws WrongInputException {
        initScanner();
        String result = stream.toString();
        if (isNumberInString(result) || !isAlphabetic(result)) {
            scanner.close();
            throw new WrongInputException();
        }
        setString(result.toLowerCase());
        scanner.close();
    }

    public void askUserString() throws WrongInputException {
        initScanner();
        String result = scanner.nextLine();
        if (isNumberInString(result) || !isAlphabetic(result)) {
            scanner.close();
            throw new WrongInputException();
        }
        setString(result.toLowerCase());
        scanner.close();
    }

    public void clear() {
        setInteger(0);
        setString(null);
    }

    public boolean isLetterInNumber(String input) {
        char[] arr = input.toCharArray();
        for (char c : arr) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
     }

    public boolean isNumberInString(String input) {
        char[] arr = input.toCharArray();
        for (char c : arr) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    public boolean isAlphabetic(String input) {
        char[] arr = input.toCharArray();
        for (char c : arr) {
            if (!Character.isAlphabetic(c)) return false;
        }
        return true;
    }
}
