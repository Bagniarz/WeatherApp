package weatherAppCore.settings.language;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.exceptions.languageImportFileException.LanguageImportFileException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Value
class LanguageProviderTest {

    PrintStream standardOut = System.out;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void testImportLanguage_NotNullEnglish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.ENGLISH, mapper);
        assertNotNull(language);
    }

    @Test
    void testImportLanguage_NotNullMapEnglish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.ENGLISH, mapper);
        assertNotNull(language.getMap());
    }

    @Test
    void testImportLanguage_NotNullMapElementEnglish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.ENGLISH, mapper);
        assertNotNull(language.getMap().get("welcome"));
    }

    @Test
    void testImportLanguage_NotNullPolish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.POLISH, mapper);
        assertNotNull(language);
    }

    @Test
    void testImportLanguage_NotNullMapPolish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.POLISH, mapper);
        assertNotNull(language.getMap());
    }

    @Test
    void testImportLanguage_NotNullMapElementPolish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.POLISH, mapper);
        assertNotNull(language.getMap().get("welcome"));
    }

    @Test
    void testImportLanguage_PolishLanguageSettings() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.POLISH, mapper);
        assertEquals(LanguageSettings.POLISH, language.getLanguageSettings());
    }

    @Test
    void testImportLanguage_EnglishLanguageSettings() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.ENGLISH, mapper);
        assertEquals(LanguageSettings.ENGLISH, language.getLanguageSettings());
    }

    @Test
    void importLanguage_English() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.ENGLISH, mapper);
        language.getMap().get("welcome").forEach(System.out::println);
        assertEquals("Welcome to WeatherForecast App :P", outputStream.toString().trim());
    }

    @Test
    void importLanguage_Polish() throws LanguageImportFileException {
        LanguageProvider languageProvider = new LanguageProvider("TEST");
        Language language = languageProvider.importLanguage(LanguageSettings.POLISH, mapper);
        language.getMap().get("welcome").forEach(System.out::println);
        assertEquals("Witam w aplikacji WeatherForecast :P", outputStream.toString().trim());
    }
}