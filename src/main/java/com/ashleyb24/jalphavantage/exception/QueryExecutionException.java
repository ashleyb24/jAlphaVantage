package com.ashleyb24.jalphavantage.exception;

public final class QueryExecutionException extends Exception {

    public QueryExecutionException(String message) {
        super(message);
    }

    public QueryExecutionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
