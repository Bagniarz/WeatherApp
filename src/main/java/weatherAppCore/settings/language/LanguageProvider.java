package weatherAppCore.settings.language;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import weatherAppCore.exceptions.LanguageImportFileException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LanguageProvider {
    final ObjectMapper mapper;

    private List<File> getFile(List<String> resourcePath) {
        List<File> result = new ArrayList<>(2);
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            for (String element : resourcePath) {
                result.add(new File((Objects.requireNonNull(classLoader.getResource(element))).toURI()));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }
        return result;
    }

    private List<String> getResourcePath(LanguageSettings languageSettings) {
        List<String> result = new ArrayList<>(3);
        switch (languageSettings) {
            case ENGLISH -> {
                result.add("languages/english.json");
                result.add("languages/weatherStatusENG.json");
                result.add("languages/englishExceptions.json");
            }
            case POLISH -> {
                result.add("languages/polish.json");
                result.add("languages/weatherStatusPL.json");
                result.add("languages/polishExceptions.json");
            }
        }
        return result;
    }

    public Language importLanguage(LanguageSettings languageSettings) throws LanguageImportFileException {
        List<File> files = getFile(getResourcePath(languageSettings));
        configureMapper();
        try {
            Map<String, List<String>> map01 = mapper.readValue(files.get(0), new TypeReference<>() {});
            Map<Integer, String> map02 = mapper.readValue(files.get(1), new TypeReference<>() {});
            Map<String, String> map03 = mapper.readValue(files.get(2), new TypeReference<>() {});
            return new Language(map01, map02, map03, languageSettings);
        } catch (IOException e) {
            throw new LanguageImportFileException(e);
        }
    }

    private void configureMapper() {
        mapper
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }
}
