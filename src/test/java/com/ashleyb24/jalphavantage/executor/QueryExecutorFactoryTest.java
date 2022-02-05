package com.ashleyb24.jalphavantage.executor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryExecutorFactoryTest {

    private static final String API_KEY = "abc";
    private static final String OK_HTTP = "OkHttp";
    private static final String REST_TEMPLATE = "RestTemplate";

    @Test
    public void testCreateDefault() {
        QueryExecutorFactory executorFactory = new QueryExecutorFactory();
        QueryExecutor executor = executorFactory.create(null, API_KEY);

        assertTrue(executor instanceof OkHttpQueryExecutor);
        assertEquals(API_KEY, ((OkHttpQueryExecutor) executor).getApiKey());
    }

    @Test
    public void testCreateOkHttp() {
        QueryExecutorFactory executorFactory = new QueryExecutorFactory();
        QueryExecutor executor = executorFactory.create(OK_HTTP, API_KEY);

        assertTrue(executor instanceof OkHttpQueryExecutor);
        assertEquals(API_KEY, ((OkHttpQueryExecutor) executor).getApiKey());
    }

    @Test
    public void testCreateRestTemplate() {
        QueryExecutorFactory executorFactory = new QueryExecutorFactory();
        QueryExecutor executor = executorFactory.create(REST_TEMPLATE, API_KEY);

        assertTrue(executor instanceof RestTemplateQueryExecutor);
        assertEquals(API_KEY, ((RestTemplateQueryExecutor) executor).getApiKey());
    }
}
