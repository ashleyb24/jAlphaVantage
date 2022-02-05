package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.Interval;
import com.ashleyb24.jalphavantage.model.Intraday;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IntradayQueryTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Intraday intraday;
    private final byte[] bytes = "String".getBytes();
    private IntradayQuery intradayQuery;

    private static final String SYMBOL = "abc";
    private static final String SYMBOL_KEY = "symbol";
    private static final Interval INTERVAL = Interval.FIVE_MIN;
    private static final String INTERVAL_KEY = "interval";

    @Test
    public void testGetQueryParams() {
        intradayQuery = new IntradayQuery(SYMBOL, INTERVAL);

        Map<String, String> queryParams = intradayQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(INTERVAL.label, queryParams.get(INTERVAL_KEY));
        assertEquals(2, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructor() {
        intradayQuery = new IntradayQuery();
        intradayQuery.setSymbol(SYMBOL);
        intradayQuery.setInterval(INTERVAL);

        Map<String, String> queryParams = intradayQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(INTERVAL.label, queryParams.get(INTERVAL_KEY));
        assertEquals(2, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoSymbol() {
        intradayQuery = new IntradayQuery();
        intradayQuery.getQueryParams();
    }

    @Test
    public void testDeserializeBytes() throws IOException, QueryExecutionException {
        intradayQuery = new IntradayQuery(SYMBOL, INTERVAL);

        when(objectMapper.readValue(bytes, Intraday.class)).thenReturn(intraday);
        Intraday response = intradayQuery.deserializeBytes(bytes, objectMapper);

        assertEquals(intraday, response);
    }

    @Test(expected = QueryExecutionException.class)
    public void testDeserializeBytesException() throws IOException, QueryExecutionException {
        intradayQuery = new IntradayQuery(SYMBOL, INTERVAL);
        when(objectMapper.readValue(bytes, Intraday.class)).thenThrow(new IOException());
        intradayQuery.deserializeBytes(bytes, objectMapper);
    }
}
