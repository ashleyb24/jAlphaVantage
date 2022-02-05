package com.ashleyb24.jalphavantage.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CurrencyExchange {
    @JsonAlias("1. From_Currency Code")
    private String fromCurrencyCode;
    @JsonAlias("2. From_Currency Name")
    private String fromCurrencyName;
    @JsonAlias("3. To_Currency Code")
    private String toCurrencyCode;
    @JsonAlias("4. To_Currency Name")
    private String toCurrencyName;
    @JsonAlias("5. Exchange Rate")
    private String exchangeRate;
    @JsonAlias("6. Last Refreshed")
    private String lastRefreshed;
    @JsonAlias("7. Time Zone")
    private String timezone;
    @JsonAlias("8. Bid Price")
    private String bidPrice;
    @JsonAlias("9. Ask Price")
    private String askPrice;
}
