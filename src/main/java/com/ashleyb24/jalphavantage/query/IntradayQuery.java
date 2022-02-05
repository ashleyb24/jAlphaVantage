package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.Interval;
import com.ashleyb24.jalphavantage.model.Intraday;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class IntradayQuery extends Query<Intraday> {

    private String symbol;
    private Interval interval;

    private static final String FUNCTION_NAME = "TIME_SERIES_INTRADAY";
    private static final String SYMBOL = "symbol";
    private static final String INTERVAL = "interval";

    public IntradayQuery() {
        super(FUNCTION_NAME);
    }

    public IntradayQuery(String symbol, Interval interval) {
        super(FUNCTION_NAME);
        this.symbol = symbol;
        this.interval = interval;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Override
    public Map<String, String> getQueryParams() throws QueryBuildingException {
        if (this.symbol == null || this.interval == null) {
            throw new QueryBuildingException("symbol and interval must both be set for IntradayQuery.");
        }
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(SYMBOL, this.symbol);
        queryParams.put(INTERVAL, this.interval.label);
        return queryParams;
    }

    @Override
    public Intraday deserializeBytes(byte[] response, ObjectMapper objectMapper) throws QueryExecutionException {
        try {
            return objectMapper.readValue(response, Intraday.class);
        } catch (IOException e) {
            throw new QueryExecutionException("Exception mapping response to object.", e);
        }
    }
}
