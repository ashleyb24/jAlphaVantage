package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.GlobalQuote;
import com.ashleyb24.jalphavantage.model.response.GlobalQuoteResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class GlobalQuoteQuery extends Query<GlobalQuote> {

    private String symbol;

    private static final String FUNCTION_NAME = "GLOBAL_QUOTE";
    private static final String SYMBOL = "symbol";

    public GlobalQuoteQuery() {
        super(FUNCTION_NAME);
    }

    public GlobalQuoteQuery(String symbol) {
        super(FUNCTION_NAME);
        this.symbol = symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Map<String, String> getQueryParams() throws QueryBuildingException {
        if (this.symbol == null) {
            throw new QueryBuildingException("Symbol must be set for GlobalQuoteQuery.");
        }
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(SYMBOL, this.symbol);
        return queryParams;
    }

    @Override
    public GlobalQuote deserializeBytes(byte[] response, ObjectMapper objectMapper) throws QueryExecutionException {
        try {
            GlobalQuoteResponse globalQuoteResponse = objectMapper.readValue(response, new TypeReference<GlobalQuoteResponse>() {});
            return globalQuoteResponse.getGlobalQuote();
        } catch (IOException e) {
            throw new QueryExecutionException("Exception mapping response to object.", e);
        }
    }

}
