package com.ashleyb24.jalphavantage.model.series;

import lombok.Data;

import java.util.Map;

@Data
public class Series {
    private Metadata metadata;
    private Map<String, TimeSeries> timeSeries;
}
