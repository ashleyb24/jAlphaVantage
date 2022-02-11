package com.ashleyb24.jalphavantage.executor;

import com.ashleyb24.jalphavantage.deserializer.SeriesDeserializer;
import com.ashleyb24.jalphavantage.model.series.Series;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class QueryExecutorFactory {

    public QueryExecutor create(String type, String apiKey) {
        ObjectMapper mapper = createObjectMapper();
        if ("OkHttp".equalsIgnoreCase(type)) {
            return new OkHttpQueryExecutor(apiKey, mapper);
        } else if ("RestTemplate".equalsIgnoreCase(type)) {
            return new RestTemplateQueryExecutor(apiKey, mapper);
        }
        return new OkHttpQueryExecutor(apiKey, mapper);
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Series.class, new SeriesDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
