package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.Interval;
import com.ashleyb24.jalphavantage.model.series.Series;
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
public class SeriesQueryTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Series series;
    private final byte[] bytes = "String".getBytes();
    private SeriesQuery seriesQuery;

    private static final String SYMBOL = "abc";
    private static final String SYMBOL_KEY = "symbol";
    private static final Interval INTERVAL = Interval.FIVE_MIN;
    private static final String INTERVAL_KEY = "interval";

    @Test
    public void testGetQueryParamsIntraday() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.INTRADAY, SYMBOL, INTERVAL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(INTERVAL.label, queryParams.get(INTERVAL_KEY));
        assertEquals(2, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructorIntraday() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.INTRADAY);
        seriesQuery.setSymbol(SYMBOL);
        seriesQuery.setInterval(INTERVAL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(INTERVAL.label, queryParams.get(INTERVAL_KEY));
        assertEquals(2, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoSymbolIntraday() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.INTRADAY);
        seriesQuery.setInterval(INTERVAL);
        seriesQuery.getQueryParams();
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoIntervalIntraday() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.INTRADAY, SYMBOL);
        seriesQuery.getQueryParams();
    }

    @Test
    public void testGetQueryParamsDaily() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.DAILY, SYMBOL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructorDaily() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.DAILY);
        seriesQuery.setSymbol(SYMBOL);
        seriesQuery.setInterval(INTERVAL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoSymbolDaily() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.DAILY);
        seriesQuery.getQueryParams();
    }

    @Test
    public void testGetQueryParamsMonthly() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.MONTHLY, SYMBOL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructorMonthly() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.MONTHLY);
        seriesQuery.setSymbol(SYMBOL);
        seriesQuery.setInterval(INTERVAL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoSymbolMonthly() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.MONTHLY);
        seriesQuery.getQueryParams();
    }

    @Test
    public void testGetQueryParamsWeekly() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.WEEKLY, SYMBOL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test
    public void testGetQueryParamsDefaultConstructorWeekly() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.WEEKLY);
        seriesQuery.setSymbol(SYMBOL);
        seriesQuery.setInterval(INTERVAL);

        Map<String, String> queryParams = seriesQuery.getQueryParams();

        assertEquals(SYMBOL, queryParams.get(SYMBOL_KEY));
        assertEquals(1, queryParams.size());
    }

    @Test(expected = QueryBuildingException.class)
    public void testGetQueryParamsDefaultConstructorNoSymbolWeekly() {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.WEEKLY);
        seriesQuery.getQueryParams();
    }

    @Test
    public void testDeserializeBytes() throws IOException, QueryExecutionException {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.INTRADAY, SYMBOL, INTERVAL);

        when(objectMapper.readValue(bytes, Series.class)).thenReturn(series);
        Series response = seriesQuery.deserializeBytes(bytes, objectMapper);

        assertEquals(series, response);
    }

    @Test(expected = QueryExecutionException.class)
    public void testDeserializeBytesException() throws IOException, QueryExecutionException {
        seriesQuery = new SeriesQuery(SeriesQuery.SeriesType.INTRADAY, SYMBOL, INTERVAL);
        when(objectMapper.readValue(bytes, Series.class)).thenThrow(new IOException());
        seriesQuery.deserializeBytes(bytes, objectMapper);
    }
}
