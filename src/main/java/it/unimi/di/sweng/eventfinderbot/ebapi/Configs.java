package it.unimi.di.sweng.eventfinderbot.ebapi;

public enum Configs {
    INSTANCE;

    private final String EBAPI_ENDPOINT;
    private final String EBAPI_TOKEN;
    private final Integer EBAPI_LIMIT;
    private final String EBAPI_SORT;

    public String EBAPI_SORT() {
        return EBAPI_SORT;
    }

    public Integer EBAPI_LIMIT() {
        return EBAPI_LIMIT;
    }

    public String EBAPI_TOKEN() {
        return EBAPI_TOKEN;
    }

    public String EBAPI_ENDPOINT() {
        return EBAPI_ENDPOINT;
    }

    private Configs() {
        EBAPI_ENDPOINT = "https://www.eventbriteapi.com/v3";
        EBAPI_TOKEN = System.getenv("EBAPI_TOKEN");
        EBAPI_LIMIT = 5;
        EBAPI_SORT = "distance";
    }
}
