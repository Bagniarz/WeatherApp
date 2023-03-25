package menu.settings.language;

import java.util.List;

public enum Language {
    POLISH(List.of(new String[] {"abc"}))
    ,
    ENGLISH(List.of(new String[] {"abc"}))
    ;
    private final List<String> language;

    Language(List<String> language) {
        this.language = language;
    }

    public List<String> getLanguage() {
        return language;
    }
}
