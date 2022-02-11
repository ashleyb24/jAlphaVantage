package com.ashleyb24.jalphavantage.model.exchange;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class CurrencyExchangeResponse {
    @JsonAlias("Realtime Currency Exchange Rate")
    private CurrencyExchange currencyExchange;
}
