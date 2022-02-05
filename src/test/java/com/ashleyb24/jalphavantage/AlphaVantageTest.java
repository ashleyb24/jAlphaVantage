package com.ashleyb24.jalphavantage;

import com.ashleyb24.jalphavantage.exception.AlphaVantageException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.executor.OkHttpQueryExecutor;
import com.ashleyb24.jalphavantage.executor.QueryExecutor;
import com.ashleyb24.jalphavantage.executor.RestTemplateQueryExecutor;
import com.ashleyb24.jalphavantage.model.GlobalQuote;
import com.ashleyb24.jalphavantage.query.GlobalQuoteQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlphaVantageTest {

    private static final String API_KEY = "abc";

    @Mock
    private QueryExecutor queryExecutor;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testExecute() throws QueryExecutionException {
        AlphaVantage alphaVantage = Mockito.spy(new AlphaVantage(API_KEY));
        GlobalQuote mockQuote = Mockito.mock(GlobalQuote.class);

        doReturn(queryExecutor).when(alphaVantage).getExecutor();
        when(queryExecutor.submit(any())).thenReturn(mockQuote);
        GlobalQuote globalQuote = alphaVantage.execute(new GlobalQuoteQuery());

        assertEquals(mockQuote, globalQuote);
    }

    @Test(expected = AlphaVantageException.class)
    public void testExecuteExceptionThrown() throws QueryExecutionException {
        AlphaVantage alphaVantage = Mockito.spy(new AlphaVantage(API_KEY));

        doReturn(queryExecutor).when(alphaVantage).getExecutor();
        when(queryExecutor.submit(any())).thenThrow(new QueryExecutionException("Test exception"));
        alphaVantage.execute(new GlobalQuoteQuery());
    }

    @Test
    public void testGetExecutorDefault() {
        AlphaVantage alphaVantage = new AlphaVantage(API_KEY);
        QueryExecutor executor = alphaVantage.getExecutor();
        assertTrue(executor instanceof OkHttpQueryExecutor);
    }

    @Test
    public void testGetExecutorRest() {
        AlphaVantage alphaVantage = new AlphaVantage(API_KEY, restTemplate);
        QueryExecutor executor = alphaVantage.getExecutor();
        assertTrue(executor instanceof RestTemplateQueryExecutor);
    }

    @Test
    public void testGetExecutorAlreadyExists() {
        AlphaVantage alphaVantage = new AlphaVantage(API_KEY);
        QueryExecutor executor = alphaVantage.getExecutor();
        QueryExecutor secondExecutor = alphaVantage.getExecutor();
        assertEquals(executor, secondExecutor);
    }


}
