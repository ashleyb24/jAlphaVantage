package com.ashleyb24.jalphavantage.model.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalQuoteResponse {
    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;
}
