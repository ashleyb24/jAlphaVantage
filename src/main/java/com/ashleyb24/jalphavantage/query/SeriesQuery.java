package com.ashleyb24.jalphavantage.query;

import com.ashleyb24.jalphavantage.deserializer.SeriesDeserializer;
import com.ashleyb24.jalphavantage.exception.QueryBuildingException;
import com.ashleyb24.jalphavantage.exception.QueryExecutionException;
import com.ashleyb24.jalphavantage.model.Interval;
import com.ashleyb24.jalphavantage.model.series.Series;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class SeriesQuery extends Query<Series> {

    private String symbol;
    private Interval interval;
    private final SeriesType seriesType;

    private static final String SYMBOL = "symbol";
    private static final String INTERVAL = "interval";

    public SeriesQuery(SeriesType seriesType) {
        super(seriesType.function);
        this.seriesType = seriesType;
    }

    public SeriesQuery(SeriesType seriesType, String symbol) {
        super(seriesType.function);
        this.seriesType = seriesType;
        this.symbol = symbol;
    }

    public SeriesQuery(SeriesType seriesType, String symbol, Interval interval) {
        super(seriesType.function);
        this.seriesType = seriesType;
        this.symbol = symbol;
        this.interval = interval;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @Override
    public Map<String, String> getQueryParams() throws QueryBuildingException {
        Map<String, String> queryParams = new HashMap<>();
        if (Arrays.asList(this.seriesType.requiredParams).contains(SYMBOL)) {
            if (this.symbol == null) {
                throw new QueryBuildingException(String.format("symbol must be set for SeriesQuery with type %s.", this.seriesType.name()));
            }
            queryParams.put(SYMBOL, this.symbol);
        }
        if (Arrays.asList(this.seriesType.requiredParams).contains(INTERVAL)) {
            if (this.interval == null) {
                throw new QueryBuildingException(String.format("interval must be set for SeriesQuery with type %s.", this.seriesType.name()));
            }
            queryParams.put(INTERVAL, this.interval.label);
        }
        return queryParams;
    }

    @Override
    public Series deserializeBytes(byte[] response, ObjectMapper objectMapper) throws QueryExecutionException {
        if (!objectMapper.canDeserialize(objectMapper.constructType(Series.class))) {
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Series.class, new SeriesDeserializer());
            objectMapper.registerModule(module);
        }
        try {
            return objectMapper.readValue(response, Series.class);
        } catch (IOException e) {
            throw new QueryExecutionException("Exception mapping response to object.", e);
        }
    }

    public enum SeriesType {
        INTRADAY("TIME_SERIES_INTRADAY", new String[]{SYMBOL, INTERVAL}),
        DAILY("TIME_SERIES_DAILY", new String[]{SYMBOL}),
        MONTHLY("TIME_SERIES_MONTHLY", new String[]{SYMBOL});

        protected final String function;
        protected final String[] requiredParams;

        SeriesType(String function, String[] requiredParams) {
            this.function = function;
            this.requiredParams = requiredParams;
        }
    }
}
