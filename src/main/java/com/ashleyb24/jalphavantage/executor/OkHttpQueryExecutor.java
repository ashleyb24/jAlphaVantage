package com.ashleyb24.jalphavantage.executor;

import com.ashleyb24.jalphavantage.query.Query;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

public final class OkHttpQueryExecutor extends AbstractQueryExecutor {

    private OkHttpClient client;

    protected OkHttpQueryExecutor(String apiKey, ObjectMapper objectMapper) {
        super(apiKey, objectMapper);
    }

    @Override
    public <T> T submit(Query<T> query) throws QueryExecutionException {
        OkHttpClient client = getClient();
        HttpUrl url = buildUrl(query);
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            Optional<ResponseBody> responseBody = Optional.ofNullable(response.body());
            if (response.isSuccessful() && responseBody.isPresent()) {
                return query.deserializeBytes(responseBody.get().bytes(), getObjectMapper());
            } else {
                throw new QueryExecutionException("Unsuccessful response returned by AlphaVantage API.");
            }
        } catch (IOException e) {
            throw new QueryExecutionException("Exception executing call to AlphaVantage API.", e);
        }
    }

    protected OkHttpClient getClient() {
        if (this.client == null) {
            this.client = new OkHttpClient();
        }
        return this.client;
    }

    protected <T> HttpUrl buildUrl(Query<T> query) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(HOST)
                .addPathSegment(PATH)
                .addQueryParameter(API_KEY, getApiKey())
                .addQueryParameter(FUNCTION, query.getFunction());
        query.getQueryParams().forEach(urlBuilder::addQueryParameter);
        return urlBuilder.build();
    }
}
