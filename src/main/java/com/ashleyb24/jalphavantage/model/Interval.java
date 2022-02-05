package com.ashleyb24.jalphavantage.model;

public enum Interval {
    ONE_MIN("1min"),
    FIVE_MIN("5min"),
    FIFTEEN_MIN("15min"),
    THIRTY_MIN("30min"),
    SIXTY_MIN("60min");

    public final String label;

    Interval(String label) {
        this.label = label;
    }

}
