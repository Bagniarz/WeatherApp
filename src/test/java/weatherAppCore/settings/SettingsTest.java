package weatherAppCore.settings;

import org.junit.jupiter.api.Test;
import weatherAppCore.exceptions.wrongInputException.components.DaysException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SettingsTest {
    SettingsFactory settingsFactory = new SettingsFactory();
    Settings settings = settingsFactory.createDefaultSettings();

    @Test
    void testSetDays_ThrowsDaysException() {
        assertThrows(DaysException.class, () -> settings.setDays(17));
    }

    @Test
    void testLoadConfig_NotNull() {
        assertNotNull(settings.getProp().get("apiKey"));
    }
}