package com.ashleyb24.jalphavantage.executor;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractQueryExecutor implements QueryExecutor {

    private final String apiKey;
    private final ObjectMapper objectMapper;

    protected static final String HOST = "www.alphavantage.co";
    protected static final String PATH = "query";
    protected static final String API_KEY = "apikey";
    protected static final String FUNCTION = "function";

    protected AbstractQueryExecutor(String apiKey, ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    protected String getApiKey() {
        return this.apiKey;
    }

    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
