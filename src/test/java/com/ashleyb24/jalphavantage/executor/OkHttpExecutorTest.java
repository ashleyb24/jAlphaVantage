package com.ashleyb24.jalphavantage.executor;

import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.GlobalQuote;
import com.ashleyb24.jalphavantage.query.GlobalQuoteQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OkHttpExecutorTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private GlobalQuoteQuery globalQuoteQuery;
    @Mock
    private GlobalQuote globalQuote;

    private MockWebServer server;

    private static final String API_KEY = "abc";
    private static final String BODY = "abc";
    private static final String TEST_ONE = "/test_one";
    private static final String TEST_TWO = "/test_two";
    private static final String TEST_THREE = "/test_three";
    private static final String FUNCTION = "abc";
    private static final String QUERY_PARAM_KEY = "Key";
    private static final String QUERY_PARAM_VALUE = "Value";
    private static final Map<String, String> QUERY_PARAM = Collections.singletonMap(QUERY_PARAM_KEY, QUERY_PARAM_VALUE);

    @Before
    public void setup() throws IOException {
        this.server = new MockWebServer();
        server.setDispatcher(dispatcher());
        server.start();
    }

    @After
    public void close() throws IOException {
        server.shutdown();
    }

    @Test
    public void testSubmit() throws QueryExecutionException {
        OkHttpQueryExecutor executor = Mockito.spy(new OkHttpQueryExecutor(API_KEY, objectMapper));
        OkHttpClient client = new OkHttpClient();

        doReturn(client).when(executor).getClient();
        doReturn(server.url(TEST_ONE)).when(executor).buildUrl(globalQuoteQuery);
        when(globalQuoteQuery.deserializeBytes(BODY.getBytes(), objectMapper)).thenReturn(globalQuote);

        GlobalQuote response = executor.submit(globalQuoteQuery);
        assertEquals(globalQuote, response);
    }

    @Test(expected = QueryExecutionException.class)
    public void testSubmitHttpError() throws QueryExecutionException {
        OkHttpQueryExecutor executor = Mockito.spy(new OkHttpQueryExecutor(API_KEY, objectMapper));
        OkHttpClient client = new OkHttpClient();

        doReturn(client).when(executor).getClient();
        doReturn(server.url(TEST_TWO)).when(executor).buildUrl(globalQuoteQuery);

        executor.submit(globalQuoteQuery);
    }

    @Test(expected = QueryExecutionException.class)
    public void testSubmitTimeout() throws QueryExecutionException {
        OkHttpQueryExecutor executor = Mockito.spy(new OkHttpQueryExecutor(API_KEY, objectMapper));
        OkHttpClient client = new OkHttpClient();

        doReturn(client).when(executor).getClient();
        doReturn(server.url(TEST_THREE)).when(executor).buildUrl(globalQuoteQuery);

        executor.submit(globalQuoteQuery);
    }

    @Test
    public void testBuildUrl() {
        OkHttpQueryExecutor executor = new OkHttpQueryExecutor(API_KEY, objectMapper);
        String expectedUrl = String.format("https://www.alphavantage.co/query?apikey=%s&function=%s&%s=%s",
                API_KEY, FUNCTION, QUERY_PARAM_KEY, QUERY_PARAM_VALUE);

        when(globalQuoteQuery.getQueryParams()).thenReturn(QUERY_PARAM);
        when(globalQuoteQuery.getFunction()).thenReturn(FUNCTION);
        HttpUrl url = executor.buildUrl(globalQuoteQuery);

        assertEquals(expectedUrl, url.toString());
    }

    @Test
    public void testSameClientIsUsed() {
        OkHttpQueryExecutor executor = new OkHttpQueryExecutor(API_KEY, objectMapper);
        OkHttpClient client = executor.getClient();
        assertEquals(client, executor.getClient());
    }

    private Dispatcher dispatcher() {
        return new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) {
                switch (recordedRequest.getPath()) {
                    case TEST_ONE:
                        return new MockResponse().setResponseCode(200).setBody(BODY);
                    case TEST_TWO:
                        return new MockResponse().setResponseCode(500);
                    case TEST_THREE:
                        return new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE);
                }
                return new MockResponse().setResponseCode(404);
            }
        };
    }
}
