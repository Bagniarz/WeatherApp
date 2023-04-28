package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import weatherAppCore.location.Location;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Value
public class FavouriteLocationsProvider {
    ObjectMapper mapper;

    public Map<String, Location> createMap(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<>() {});
    }
}
