package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import weatherAppCore.location.Location;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Value
public class FavouriteLocationsSaver {
    ObjectMapper mapper;
    ClassLoader loader = getClass().getClassLoader();

    public void createJSONFile(Map<String, Location> map, File file) throws IOException {
        mapper.writeValue(file, map);
    }
}
