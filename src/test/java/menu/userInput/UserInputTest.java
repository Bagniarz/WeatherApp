package menu.userInput;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserInputTest {

    @Test
    void testAskUserString_Equals() {
        try(InputStream ignored = System.in) {
            UserInput userInput = new UserInput("");
            ByteArrayInputStream in = new ByteArrayInputStream("Washington".getBytes());
            System.setIn(in);
            userInput.askUserString();
            assertEquals("washington", userInput.getInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAskUserString_ReturnNull() {
        try(InputStream ignored = System.in) {
            UserInput userInput = new UserInput("");
            ByteArrayInputStream in = new ByteArrayInputStream("Wars2aw".getBytes());
            System.setIn(in);
            userInput.askUserString();
            assertNull(userInput.getInput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void isNumberInString_True() {
        UserInput userInput = new UserInput("");
        assertTrue(userInput.isNumberInString("wars2aw"));
    }

    @Test
    void isNumberInString_False() {
        UserInput userInput = new UserInput("");
        assertFalse(userInput.isNumberInString("washington"));
    }
    @Test
    void isAlphabetic_True() {
        UserInput userInput = new UserInput("");
        assertTrue(userInput.isAlphabetic("washington"));
    }
    @Test
    void isAlphabetic_False() {
        UserInput userInput = new UserInput("");
        assertFalse(userInput.isAlphabetic("wa%*&gton"));
    }
}