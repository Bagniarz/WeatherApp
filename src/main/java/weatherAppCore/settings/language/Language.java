package weatherAppCore.settings.language;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Jacksonized
public class Language {
    Map<String, List<String>> map;
    Map<Integer, String> weatherStatus;
    Map<String, String> errMessMap;
    LanguageSettings languageSettings;
}
