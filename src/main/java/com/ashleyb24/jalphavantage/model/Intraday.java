package com.ashleyb24.jalphavantage.model;

import lombok.Data;

import java.util.Map;

@Data
public class Intraday {
    private IntradayMetadata metadata;
    private Map<String, IntradayTimeSeries> timeSeries;
}
