package com.ashleyb24.jalphavantage.deserializer;

import com.ashleyb24.jalphavantage.model.Intraday;
import com.ashleyb24.jalphavantage.model.IntradayMetadata;
import com.ashleyb24.jalphavantage.model.IntradayTimeSeries;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class IntradayDeserializerTest {

    private Intraday intraday;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Intraday.class, new IntradayDeserializer());
        objectMapper.registerModule(module);
        intraday = objectMapper.readValue(new File("src/test/resources/data/IntradayRespnse.json"), Intraday.class);
    }

    @Test
    public void testMetadata() {
        IntradayMetadata metadata = intraday.getMetadata();
        assertNotNull(metadata);
        assertEquals("Intraday (60min) open, high, low, close prices and volume", metadata.getInformation());
        assertEquals("IBM", metadata.getSymbol());
        assertEquals("2022-02-03 19:00:00", metadata.getLastRefreshed());
        assertEquals("Compact", metadata.getOutputSize());
        assertEquals("US/Eastern", metadata.getTimeZone());
    }

    @Test
    public void testTimeSeries() {
        Map<String, IntradayTimeSeries> timeSeries = intraday.getTimeSeries();
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
