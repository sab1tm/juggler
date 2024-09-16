package kz.sab1tm.juggler.models.enums;

public enum HttpMethodEnum {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(true),
    PATCH(true),
    HEAD(false),
    OPTIONS(true);

    private final boolean allowsRequestBody;

    HttpMethodEnum(boolean allowsRequestBody) {
        this.allowsRequestBody = allowsRequestBody;
    }

    public boolean allowsRequestBody() {
        return allowsRequestBody;
    }
}
