package com.ashleyb24.jalphavantage.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class IntradayTimeSeries {
    @JsonAlias("01. open")
    private String open;
    @JsonAlias("02. high")
    private String high;
    @JsonAlias("03. low")
    private String low;
    @JsonAlias("04. close")
    private String close;
    @JsonAlias("05. volume")
    private String volume;
}
