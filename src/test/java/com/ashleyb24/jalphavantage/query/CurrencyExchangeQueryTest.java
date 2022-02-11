package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.exchange.CurrencyExchange;
import com.ashleyb24.jalphavantage.model.exchange.CurrencyExchangeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyExchangeQueryTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CurrencyExchange currencyExchange;
    private final byte[] bytes = "String".getBytes();
    private CurrencyExchangeQuery currencyExchangeQuery;

    private static final String FROM_CURRENCY = "abc";
    private static final String FROM_CURRENCY_KEY = "from_currency";
    private static final String TO_CURRENCY = "abc";
    private static final String TO_CURRENCY_KEY = "to_currency";

    @Test
    public void testGetQueryParams() {
        currencyExchangeQuery = new CurrencyExchangeQuery(FROM_CURRENCY, TO_CURRENCY);

        Map<String, String> queryParams = currencyExchangeQuery.getQueryParams();

        assertEquals(FROM_CURRENCY, queryParams.get(FROM_CURRENCY_KEY));
        assertEquals(TO_CURRENCY, queryParams.get(TO_CURRENCY_KEY));
        assertEquals(2, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructor() {
        currencyExchangeQuery = new CurrencyExchangeQuery();
        currencyExchangeQuery.setFromCurrency(FROM_CURRENCY);
        currencyExchangeQuery.setToCurrency(TO_CURRENCY);

        Map<String, String> queryParams = currencyExchangeQuery.getQueryParams();

        assertEquals(FROM_CURRENCY, queryParams.get(FROM_CURRENCY_KEY));
        assertEquals(TO_CURRENCY, queryParams.get(TO_CURRENCY_KEY));
        assertEquals(2, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorMissingParameters() {
        currencyExchangeQuery = new CurrencyExchangeQuery();
        currencyExchangeQuery.getQueryParams();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeserializeBytes() throws IOException, QueryExecutionException {
        currencyExchangeQuery = new CurrencyExchangeQuery(FROM_CURRENCY, TO_CURRENCY);
        CurrencyExchangeResponse currencyExchangeResponse = new CurrencyExchangeResponse();
        currencyExchangeResponse.setCurrencyExchange(currencyExchange);

        when(objectMapper.readValue(eq(bytes), any(TypeReference.class))).thenReturn(currencyExchangeResponse);
        CurrencyExchange response = currencyExchangeQuery.deserializeBytes(bytes, objectMapper);

        assertEquals(currencyExchange, response);
    }

    @Test(expected = QueryExecutionException.class)
    @SuppressWarnings("unchecked")
    public void testDeserializeBytesException() throws IOException, QueryExecutionException {
        currencyExchangeQuery = new CurrencyExchangeQuery(FROM_CURRENCY, TO_CURRENCY);
        when(objectMapper.readValue(eq(bytes), any(TypeReference.class))).thenThrow(new IOException());
        currencyExchangeQuery.deserializeBytes(bytes, objectMapper);
    }

}
