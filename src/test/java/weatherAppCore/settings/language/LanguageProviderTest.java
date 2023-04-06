package weatherAppCore.settings.language;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageProviderTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void getURL() {
    }

    @Test
    void importLanguageEnglish() {
        LanguageProvider languageProvider = new LanguageProvider();
        Map<String, List<String>> language = languageProvider.importLanguage(LanguageSettings.ENGLISH);
        language.get("welcome").forEach(System.out::println);
        assertEquals("Welcome to WeatherForecast App :P", outputStream.toString().trim());
    }
}