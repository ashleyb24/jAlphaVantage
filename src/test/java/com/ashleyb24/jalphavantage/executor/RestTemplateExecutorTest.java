package com.ashleyb24.jalphavantage.executor;

import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.GlobalQuote;
import com.ashleyb24.jalphavantage.query.GlobalQuoteQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestTemplateExecutorTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private GlobalQuoteQuery globalQuoteQuery;
    @Mock
    private GlobalQuote globalQuote;

    private static final String API_KEY = "abc";
    private static final String BODY = "abc";
    private static final String FUNCTION = "abc";
    private static final String QUERY_PARAM_KEY = "Key";
    private static final String QUERY_PARAM_VALUE = "Value";
    private static final Map<String, String> QUERY_PARAM = Collections.singletonMap(QUERY_PARAM_KEY, QUERY_PARAM_VALUE);

    @Test
    public void testSubmit() throws QueryExecutionException {
        RestTemplateQueryExecutor executor = new RestTemplateQueryExecutor(API_KEY, objectMapper);
        executor.setRestTemplate(restTemplate);

        String expectedUrl = String.format("https://www.alphavantage.co/query?apikey=%s&function=%s&%s=%s",
                API_KEY, FUNCTION, QUERY_PARAM_KEY, QUERY_PARAM_VALUE);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(BODY.getBytes(), HttpStatus.OK);

        when(globalQuoteQuery.getQueryParams()).thenReturn(QUERY_PARAM);
        when(globalQuoteQuery.getFunction()).thenReturn(FUNCTION);
        when(restTemplate.getForEntity(expectedUrl, byte[].class)).thenReturn(responseEntity);
        when(globalQuoteQuery.deserializeBytes(BODY.getBytes(), objectMapper)).thenReturn(globalQuote);

        GlobalQuote response = executor.submit(globalQuoteQuery);
        assertEquals(globalQuote, response);
    }

    @Test(expected = QueryExecutionException.class)
    public void testSubmitNoResponseBody() throws QueryExecutionException {
        RestTemplateQueryExecutor executor = new RestTemplateQueryExecutor(API_KEY, objectMapper);
        executor.setRestTemplate(restTemplate);

        String expectedUrl = String.format("https://www.alphavantage.co/query?apikey=%s&function=%s&%s=%s",
                API_KEY, FUNCTION, QUERY_PARAM_KEY, QUERY_PARAM_VALUE);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(globalQuoteQuery.getQueryParams()).thenReturn(QUERY_PARAM);
        when(globalQuoteQuery.getFunction()).thenReturn(FUNCTION);
        when(restTemplate.getForEntity(expectedUrl, byte[].class)).thenReturn(responseEntity);

        executor.submit(globalQuoteQuery);
    }

    @Test(expected = QueryExecutionException.class)
    public void testSubmitHttpError() throws QueryExecutionException {
        RestTemplateQueryExecutor executor = new RestTemplateQueryExecutor(API_KEY, objectMapper);
        executor.setRestTemplate(restTemplate);

        String expectedUrl = String.format("https://www.alphavantage.co/query?apikey=%s&function=%s&%s=%s",
                API_KEY, FUNCTION, QUERY_PARAM_KEY, QUERY_PARAM_VALUE);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(BODY.getBytes(), HttpStatus.INTERNAL_SERVER_ERROR);

        when(globalQuoteQuery.getQueryParams()).thenReturn(QUERY_PARAM);
        when(globalQuoteQuery.getFunction()).thenReturn(FUNCTION);
        when(restTemplate.getForEntity(expectedUrl, byte[].class)).thenReturn(responseEntity);

        executor.submit(globalQuoteQuery);
    }
}
