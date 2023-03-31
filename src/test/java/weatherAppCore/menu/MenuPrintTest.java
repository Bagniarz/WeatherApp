package weatherAppCore.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuPrintTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void Test_PrintResult() {
        MenuPrint menuPrint = new MenuPrint("TEST");
        menuPrint.print();
        assertEquals("TEST", outputStreamCaptor.toString().trim());
    }

    @Test
    void Test_clearString() {
        MenuPrint menuPrintTest01 = new MenuPrint();
        MenuPrint menuPrintTest02 = new MenuPrint("Test");
        menuPrintTest02.clear();
        assertEquals(menuPrintTest01, menuPrintTest02);
    }
}