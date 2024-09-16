package kz.sab1tm.juggler.models;

import java.util.HashMap;

public class HttpRequestParams {

    private final HashMap<String, String> params = new HashMap<>();

    public void add(String key, String value) {
        params.put(key, value);
    }

    public void remove(String key) {
        params.remove(key);
    }

    public HashMap<String, String> getParams() {
        return params;
    }
}
