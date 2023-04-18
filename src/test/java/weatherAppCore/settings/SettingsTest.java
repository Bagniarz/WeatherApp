package weatherAppCore.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weatherAppCore.exceptions.wrongInputException.components.DaysException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SettingsTest {
    private final SettingsFactory settingsFactory = new SettingsFactory();
    private Settings settings;

    @BeforeEach
    void reset() {
        settings = settingsFactory.createDefaultSettings();
    }

    @Test
    void testSetDays_ThrowsDaysException() {
        assertThrows(DaysException.class, () -> settings.setDays(17));
    }

    @Test
    void testLoadConfig_NotNull() {
        assertNotNull(settings.getProp().get("apiKey"));
    }
}