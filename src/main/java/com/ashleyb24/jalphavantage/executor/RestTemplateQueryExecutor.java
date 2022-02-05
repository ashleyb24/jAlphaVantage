package com.ashleyb24.jalphavantage.executor;

import com.ashleyb24.jalphavantage.query.Query;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public final class RestTemplateQueryExecutor extends AbstractQueryExecutor {

    private RestTemplate restTemplate;

    protected RestTemplateQueryExecutor(String apiKey, ObjectMapper objectMapper) {
        super(apiKey, objectMapper);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T submit(Query<T> query) throws QueryExecutionException {
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(HOST)
                .path(PATH)
                .queryParam(API_KEY, getApiKey())
                .queryParam(FUNCTION, query.getFunction());
        query.getQueryParams().forEach(urlBuilder::queryParam);
        String url = urlBuilder.toUriString();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        if (!(response.hasBody() && response.getStatusCode().is2xxSuccessful())) {
            throw new QueryExecutionException("No response body returned by AlphaVantage API.");
        }
        return query.deserializeBytes(response.getBody(), getObjectMapper());
    }
}
