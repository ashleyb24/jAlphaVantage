package com.ashleyb24.jalphavantage.executor;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class QueryExecutorFactory {

    public QueryExecutor create(String type, String apiKey) {
        ObjectMapper mapper = new ObjectMapper();
        if ("OkHttp".equalsIgnoreCase(type)) {
            return new OkHttpQueryExecutor(apiKey, mapper);
        } else if ("RestTemplate".equalsIgnoreCase(type)) {
            return new RestTemplateQueryExecutor(apiKey, mapper);
        }
        return new OkHttpQueryExecutor(apiKey, mapper);
    }
}
