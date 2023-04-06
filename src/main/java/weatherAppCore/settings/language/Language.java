package weatherAppCore.settings.language;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Jacksonized
public class Language {
    Map<String, List<String>> map;
}
