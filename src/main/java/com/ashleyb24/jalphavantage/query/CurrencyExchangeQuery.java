package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.exchange.CurrencyExchange;
import com.ashleyb24.jalphavantage.model.exchange.CurrencyExchangeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class CurrencyExchangeQuery extends Query<CurrencyExchange> {

    private String fromCurrency;
    private String toCurrency;

    private static final String FUNCTION_NAME = "CURRENCY_EXCHANGE_RATE";
    private static final String FROM_CURRENCY = "from_currency";
    private static final String TO_CURRENCY = "to_currency";

    public CurrencyExchangeQuery() {
        super(FUNCTION_NAME);
    }

    public CurrencyExchangeQuery(String fromCurrency, String toCurrency) {
        super(FUNCTION_NAME);
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Override
    public Map<String, String> getQueryParams() throws QueryBuildingException {
        if (this.fromCurrency == null || this.toCurrency == null) {
            throw new QueryBuildingException("fromCurrency and toCurrency must both be set for CurrencyExchangeQuery.");
        }
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(FROM_CURRENCY, this.fromCurrency);
        queryParams.put(TO_CURRENCY, this.toCurrency);
        return queryParams;
    }

    @Override
    public CurrencyExchange deserializeBytes(byte[] response, ObjectMapper objectMapper) throws QueryExecutionException {
        try {
            CurrencyExchangeResponse currencyExchangeResponse = objectMapper.readValue(response, new TypeReference<CurrencyExchangeResponse>() {});
            return currencyExchangeResponse.getCurrencyExchange();
        } catch (IOException e) {
            throw new QueryExecutionException("Exception mapping response to object.", e);
        }
    }
}
