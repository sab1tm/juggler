package kz.sab1tm.juggler.utils;

import java.net.URL;
import java.util.Objects;

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static URL getResource(String path) {
        URL resource = ResourceUtil.class.getResource(path);
        return Objects.requireNonNull(resource);
    }

    public static String getResourceAsString(String path) {
        URL resource = ResourceUtil.class.getResource(path);
        return Objects.requireNonNull(resource).toExternalForm();
    }
}
