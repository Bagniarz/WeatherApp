package weatherAppCore.settings.language;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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

    private InputStream getWeatherStatusStream(LanguageSettings languageSettings) {
        InputStream ioStream;
        if (languageSettings.equals(LanguageSettings.ENGLISH)) {
            ioStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("languages/weatherStatusENG.json");
            return ioStream;
        } else {
            ioStream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("languages/weatherStatusPL.json");
            return ioStream;
        }
    }

    public Language importLanguage(LanguageSettings languageSettings) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            Map<String, List<String>> map01 = mapper.readValue(getStream(languageSettings), new TypeReference<>() {});
            Map<Integer, String> map02 = mapper.readValue(getWeatherStatusStream(languageSettings), new TypeReference<>() {});
            return new Language(map01, map02);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
