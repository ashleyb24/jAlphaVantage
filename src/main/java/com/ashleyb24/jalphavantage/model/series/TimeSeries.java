package com.ashleyb24.jalphavantage.model.series;

import lombok.Data;

@Data
public class TimeSeries {
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
}
