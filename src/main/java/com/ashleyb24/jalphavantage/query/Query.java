package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class Query<T> {

    private final String function;

    protected Query(String function) {
        this.function = function;
    }

    public String getFunction() {
        return this.function;
    }

    public abstract Map<String, String> getQueryParams() throws QueryBuildingException;
    public abstract T deserializeBytes(byte[] response, ObjectMapper objectMapper) throws QueryExecutionException;

}
