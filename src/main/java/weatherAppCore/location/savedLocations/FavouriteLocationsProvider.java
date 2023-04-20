package weatherAppCore.location.savedLocations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import weatherAppCore.location.Location;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

@Value
public class FavouriteLocationsProvider {
    ObjectMapper mapper;

    public Map<String, Location> createMap() throws URISyntaxException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return mapper.readValue(new File(Objects.requireNonNull(classLoader.getResource("favouriteLocations.json")).toURI()), new TypeReference<>() {});
    }

}
