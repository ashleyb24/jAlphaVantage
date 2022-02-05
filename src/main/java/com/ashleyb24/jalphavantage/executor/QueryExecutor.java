package com.ashleyb24.jalphavantage.executor;

import com.ashleyb24.jalphavantage.query.Query;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;

public interface QueryExecutor {
    <T> T submit(Query<T> query) throws QueryExecutionException;
}
