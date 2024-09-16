package kz.sab1tm.juggler.models;

import java.util.HashMap;

public class HttpRequestHeaders {

    private final HashMap<String, String> headers = new HashMap<>();

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public void remove(String key) {
        headers.remove(key);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
