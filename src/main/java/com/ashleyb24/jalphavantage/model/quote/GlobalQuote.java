package com.ashleyb24.jalphavantage.model.quote;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Generated;

@Data
public class GlobalQuote {
    @JsonAlias("01. symbol")
    private String symbol;
    @JsonAlias("02. open")
    private String open;
    @JsonAlias("03. high")
    private String high;
    @JsonAlias("04. low")
    private String low;
    @JsonAlias("05. price")
    private String price;
    @JsonAlias("06. volume")
    private String volume;
    @JsonAlias("07. latest trading day")
    private String latestTradingDay;
    @JsonAlias("08. previous close")
    private String previousClose;
    @JsonAlias("09. change")
    private String change;
    @JsonAlias("10. change percent")
    private String changePercent;
}
