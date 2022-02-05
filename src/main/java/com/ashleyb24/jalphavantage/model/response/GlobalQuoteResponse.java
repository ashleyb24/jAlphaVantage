package com.ashleyb24.jalphavantage.model.response;

import com.ashleyb24.jalphavantage.model.GlobalQuote;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalQuoteResponse {
    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;
}
