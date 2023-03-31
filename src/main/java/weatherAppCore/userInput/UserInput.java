package weatherAppCore.userInput;

import java.util.Scanner;

public class UserInput {
    private String input;

    public UserInput(){}

    public UserInput(String input) {
        this.input = input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void askUserString() {
        try(Scanner scanner = new Scanner((System.in))) {
            String result = scanner.nextLine();
            if (isNumberInString(result) || !isAlphabetic(result)) {
                setInput(null);
                return;
            }
            setInput(result.toLowerCase());
        }
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
