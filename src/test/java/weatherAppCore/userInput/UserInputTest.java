package weatherAppCore.userInput;

import org.junit.jupiter.api.Test;
import weatherAppCore.exceptions.wrongInputException.WrongInputException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserInputTest {
    @Test
    void testAskUserString_Equals() throws WrongInputException {
        try(InputStream ignored = System.in) {
            UserInput userInput = UserInput.builder().build();
            ByteArrayInputStream in = new ByteArrayInputStream("Washington".getBytes());
            System.setIn(in);
            userInput.askUserString();
            assertEquals("washington", userInput.getString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAskUserString_throwsWrongInputException() {
        try(InputStream ignored = System.in) {
            UserInput userInput = UserInput.builder().build();
            ByteArrayInputStream in = new ByteArrayInputStream("Wars2aw".getBytes());
            System.setIn(in);
            assertThrows(WrongInputException.class, userInput::askUserString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isNumberInString_True() {
        UserInput userInput = UserInput.builder().build();
        assertTrue(userInput.isNumberInString("wars2aw"));
    }

    @Test
    void isNumberInString_False() {
        UserInput userInput = UserInput.builder().build();
        assertFalse(userInput.isNumberInString("washington"));
    }
    @Test
    void isAlphabetic_True() {
        UserInput userInput = UserInput.builder().build();
        assertTrue(userInput.isAlphabetic("washington"));
    }
    @Test
    void isAlphabetic_False() {
        UserInput userInput = UserInput.builder().build();
        assertFalse(userInput.isAlphabetic("wa%*&gton"));
    }

    @Test
    void isStringInNumber_True() {
        UserInput userInput = UserInput.builder().build();
        assertTrue(userInput.isLetterInNumber("23"));
    }

    @Test
    void isStringInNumber_False() {
        UserInput userInput = UserInput.builder().build();
        assertFalse(userInput.isLetterInNumber("2b"));
    }
}