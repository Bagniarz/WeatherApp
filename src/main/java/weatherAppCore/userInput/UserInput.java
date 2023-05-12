package weatherAppCore.userInput;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;

import java.util.Scanner;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserInput {
    String string;
    int integer;

    public void askUserInt(Scanner scanner) throws WrongInputException {
        String result = scanner.nextLine().trim();
        if (result.isEmpty() || result.length() > 2 || !isLetterInNumber(result)) {
            throw new WrongInputException();
        }
        setInteger(Integer.parseInt(result));
    }

    public void askUserString(Scanner scanner) throws WrongInputException {
        String result = scanner.nextLine().trim();
        if (result.isEmpty() || isNumberInString(result) || !isAlphabetic(result)) {
            throw new WrongInputException();
        }
        setString(result.toLowerCase());
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
