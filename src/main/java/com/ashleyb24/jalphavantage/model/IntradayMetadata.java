package com.ashleyb24.jalphavantage.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class IntradayMetadata {
    @JsonAlias("1. Information")
    private String information;
    @JsonAlias("2. Symbol")
    private String symbol;
    @JsonAlias("3. Last Refreshed")
    private String lastRefreshed;
    @JsonAlias("4. Interval")
    private String interval;
    @JsonAlias("5. Output Size")
    private String outputSize;
    @JsonAlias("6. Time Zone")
    private String timeZone;
}
