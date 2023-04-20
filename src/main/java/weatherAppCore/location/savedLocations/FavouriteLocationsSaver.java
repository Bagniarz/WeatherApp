package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import weatherAppCore.location.Location;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

@Value
public class FavouriteLocationsSaver {
    ObjectMapper mapper;

    public void createJSONFile(Map<String, Location> map) throws IOException {
        mapper.writeValue(Paths.get("favouriteLocations.json").toFile(), map);
    }
}
