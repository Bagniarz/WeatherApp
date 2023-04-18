package weatherAppCore.userInput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserInputTest {

    private final UserInput userInput = UserInput.builder().build();

    @AfterEach
    void reset() {
        userInput.clear();
    }

    @Test
    void testAskUserString_Equals() throws WrongInputException {
        ByteArrayInputStream in = new ByteArrayInputStream("Washington".getBytes());
        try(Scanner scanner = new Scanner(in)) {
            userInput.askUserString(scanner);
            assertEquals("washington", userInput.getString());
        }
    }

    @Test
    void testAskUserString_throwsWrongInputException() {
        ByteArrayInputStream in = new ByteArrayInputStream("Wars2aw".getBytes());
        try(Scanner scanner = new Scanner(in)) {
            assertThrows(WrongInputException.class, () -> userInput.askUserString(scanner));
        }
    }

    @Test
    void isNumberInString_True() {
        assertTrue(userInput.isNumberInString("wars2aw"));
    }

    @Test
    void isNumberInString_False() {
        assertFalse(userInput.isNumberInString("washington"));
    }
    @Test
    void isAlphabetic_True() {
        assertTrue(userInput.isAlphabetic("washington"));
    }
    @Test
    void isAlphabetic_False() {
        assertFalse(userInput.isAlphabetic("wa%*&gton"));
    }

    @Test
    void isStringInNumber_True() {
        assertTrue(userInput.isLetterInNumber("23"));
    }

    @Test
    void isStringInNumber_False() {
        assertFalse(userInput.isLetterInNumber("2b"));
    }
}