package com.ashleyb24.jalphavantage.model.series;

import lombok.Data;

@Data
public class Metadata {
    private String information;
    private String symbol;
    private String lastRefreshed;
    private String interval;
    private String outputSize;
    private String timeZone;
}
