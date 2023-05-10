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

    public void createJSONFile(Map<Integer, Location> map, File file) throws IOException {
        mapper.writeValue(file, map);
    }
}
