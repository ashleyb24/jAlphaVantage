package com.ashleyb24.jalphavantage.model.response;

import com.ashleyb24.jalphavantage.model.CurrencyExchange;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CurrencyExchangeResponse {
    @JsonAlias("Realtime Currency Exchange Rate")
    private CurrencyExchange currencyExchange;
}
