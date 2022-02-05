package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.GlobalQuote;
import com.ashleyb24.jalphavantage.model.response.GlobalQuoteResponse;
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
public class GlobalQuoteQueryTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private GlobalQuote globalQuote;
    private final byte[] bytes = "String".getBytes();
    private GlobalQuoteQuery globalQuoteQuery;

    private static final String SYMBOL = "abc";
    private static final String SYMBOL_KEY = "symbol";

    @Test
    public void testGetQueryParams() {
        globalQuoteQuery = new GlobalQuoteQuery(SYMBOL);

        Map<String, String> queryParams = globalQuoteQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructor() {
        globalQuoteQuery = new GlobalQuoteQuery();
        globalQuoteQuery.setSymbol(SYMBOL);

        Map<String, String> queryParams = globalQuoteQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoSymbol() {
        globalQuoteQuery = new GlobalQuoteQuery();
        globalQuoteQuery.getQueryParams();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeserializeBytes() throws IOException, QueryExecutionException {
        globalQuoteQuery = new GlobalQuoteQuery(SYMBOL);
        GlobalQuoteResponse globalQuoteResponse = new GlobalQuoteResponse();
        globalQuoteResponse.setGlobalQuote(globalQuote);

        when(objectMapper.readValue(eq(bytes), any(TypeReference.class))).thenReturn(globalQuoteResponse);
        GlobalQuote response = globalQuoteQuery.deserializeBytes(bytes, objectMapper);

        assertEquals(globalQuote, response);
    }

    @Test(expected = QueryExecutionException.class)
    @SuppressWarnings("unchecked")
    public void testDeserializeBytesException() throws IOException, QueryExecutionException {
        globalQuoteQuery = new GlobalQuoteQuery(SYMBOL);
        when(objectMapper.readValue(eq(bytes), any(TypeReference.class))).thenThrow(new IOException());
        globalQuoteQuery.deserializeBytes(bytes, objectMapper);
    }

}
