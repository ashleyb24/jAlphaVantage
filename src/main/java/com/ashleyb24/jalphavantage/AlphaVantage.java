package com.ashleyb24.jalphavantage;

import com.ashleyb24.jalphavantage.exception.AlphaVantageException;
import com.ashleyb24.jalphavantage.executor.QueryExecutor;
import com.ashleyb24.jalphavantage.executor.QueryExecutorFactory;
import com.ashleyb24.jalphavantage.executor.RestTemplateQueryExecutor;
import com.ashleyb24.jalphavantage.query.Query;
import org.springframework.web.client.RestTemplate;

public class AlphaVantage {

    private final String apiKey;
    private QueryExecutor executor;
    private RestTemplate restTemplate;

    public AlphaVantage(String apiKey) {
        this.apiKey = apiKey;
    }

    public AlphaVantage(String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public <T> T execute(Query<T> query) {
        QueryExecutor executor = getExecutor();
        try {
            return executor.submit(query);
        } catch (Exception e) {
            throw new AlphaVantageException("Exception executing AlphaVantage query", e);
        }
    }

    protected QueryExecutor getExecutor() {
        if (this.executor == null && this.restTemplate != null) {
            RestTemplateQueryExecutor executor = (RestTemplateQueryExecutor) new QueryExecutorFactory().create("RestTemplate", this.apiKey);
            executor.setRestTemplate(restTemplate);
            this.executor = executor;
        } else if (this.executor == null) {
            this.executor = new QueryExecutorFactory().create("OkHttp", this.apiKey);
        }
        return this.executor;
    }

}
