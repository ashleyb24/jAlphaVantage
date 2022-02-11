package com.ashleyb24.jalphavantage.deserializer;

import com.ashleyb24.jalphavantage.model.series.Metadata;
import com.ashleyb24.jalphavantage.model.series.Series;
import com.ashleyb24.jalphavantage.model.series.TimeSeries;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class SeriesDeserializerTest {

    private Series intradaySeries;
    private Series dailySeries;
    private Series monthlySeries;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Series.class, new SeriesDeserializer());
        objectMapper.registerModule(module);
        intradaySeries = objectMapper.readValue(new File("src/test/resources/data/IntradayResponse.json"), Series.class);
        dailySeries = objectMapper.readValue(new File("src/test/resources/data/DailyResponse.json"), Series.class);
        monthlySeries = objectMapper.readValue(new File("src/test/resources/data/MonthlyResponse.json"), Series.class);
    }

    @Test
    public void testMetadata() {
        Metadata metadata = intradaySeries.getMetadata();
        assertNotNull(metadata);
        assertEquals("Intraday (60min) open, high, low, close prices and volume", metadata.getInformation());
        assertEquals("IBM", metadata.getSymbol());
        assertEquals("2022-02-03 19:00:00", metadata.getLastRefreshed());
        assertEquals("Compact", metadata.getOutputSize());
        assertEquals("US/Eastern", metadata.getTimeZone());
        assertEquals("60min", metadata.getInterval());
    }

    @Test
    public void testTimeSeries() {
        Map<String, TimeSeries> timeSeries = intradaySeries.getTimeSeries();
        assertNotNull(timeSeries);
        assertFalse(timeSeries.isEmpty());
        timeSeries.forEach((key, value) -> {
            assertNotNull(value.getOpen());
            assertNotNull(value.getHigh());
            assertNotNull(value.getLow());
            assertNotNull(value.getClose());
            assertNotNull(value.getVolume());
        });
    }

    @Test
    public void testDailyMetadata() {
        Metadata metadata = dailySeries.getMetadata();
        assertNotNull(metadata);
        assertEquals("Daily Prices (open, high, low, close) and Volumes", metadata.getInformation());
        assertEquals("IBM", metadata.getSymbol());
        assertEquals("2022-02-09", metadata.getLastRefreshed());
        assertEquals("Compact", metadata.getOutputSize());
        assertEquals("US/Eastern", metadata.getTimeZone());
        assertNull(metadata.getInterval());
    }

    @Test
    public void testDailyTimeSeries() {
        Map<String, TimeSeries> timeSeries = dailySeries.getTimeSeries();
        assertNotNull(timeSeries);
        assertFalse(timeSeries.isEmpty());
        timeSeries.forEach((key, value) -> {
            assertNotNull(value.getOpen());
            assertNotNull(value.getHigh());
            assertNotNull(value.getLow());
            assertNotNull(value.getClose());
            assertNotNull(value.getVolume());
        });
    }

    @Test
    public void testMonthlyMetadata() {
        Metadata metadata = monthlySeries.getMetadata();
        assertNotNull(metadata);
        assertEquals("Monthly Prices (open, high, low, close) and Volumes", metadata.getInformation());
        assertEquals("IBM", metadata.getSymbol());
        assertEquals("2022-02-10", metadata.getLastRefreshed());
        assertEquals("US/Eastern", metadata.getTimeZone());
        assertNull(metadata.getInterval());
        assertNull(metadata.getOutputSize());
    }

    @Test
    public void testMonthlyTimeSeries() {
        Map<String, TimeSeries> timeSeries = monthlySeries.getTimeSeries();
        assertNotNull(timeSeries);
        assertFalse(timeSeries.isEmpty());
        timeSeries.forEach((key, value) -> {
            assertNotNull(value.getOpen());
            assertNotNull(value.getHigh());
            assertNotNull(value.getLow());
            assertNotNull(value.getClose());
            assertNotNull(value.getVolume());
        });
    }
}
