package weatherAppCore.userInput;

import lombok.*;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;

import java.util.Scanner;

@Data
@Builder
public class UserInput {
    private String string, excMess;
    private int integer;
    @Getter @Setter (AccessLevel.NONE)
    private Scanner scanner;

    private void initScanner() {
        this.scanner = new Scanner(System.in);
    }

    public void askUserInt() throws WrongInputException {
        initScanner();
        String result = scanner.nextLine();
        if (result.length() > 2 ) {
            throw new WrongInputException(excMess);
        }
        if (!isLetterInNumber(result)) {
            throw new WrongInputException(excMess);
        }
        System.out.println("Input: " + result);
        setInteger(Integer.parseInt(result));
    }

    public void askUserString() throws WrongInputException {
        initScanner();
        String result = scanner.nextLine();
        if (isNumberInString(result) || !isAlphabetic(result)) {
            throw new WrongInputException(excMess);
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
