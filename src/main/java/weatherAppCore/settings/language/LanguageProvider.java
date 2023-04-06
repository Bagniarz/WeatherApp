package weatherAppCore.settings.language;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class LanguageProvider {
    private InputStream getStream(LanguageSettings languageSettings) {
        InputStream ioStream;
        if (languageSettings.equals(LanguageSettings.ENGLISH)) {
            ioStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("languages/english.json");
            return ioStream;
        } else {
            ioStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("languages/polish.json");
            return ioStream;
        }
    }

    public Map<String, List<String>> importLanguage(LanguageSettings languageSettings) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(getStream(languageSettings), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Language getLanguage(LanguageSettings languageSettings) {
        return new Language(importLanguage(languageSettings));
    }
}
