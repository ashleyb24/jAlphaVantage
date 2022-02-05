package com.ashleyb24.jalphavantage.deserializer;

import com.ashleyb24.jalphavantage.model.Intraday;
import com.ashleyb24.jalphavantage.model.IntradayMetadata;
import com.ashleyb24.jalphavantage.model.IntradayTimeSeries;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class IntradayDeserializer extends JsonDeserializer<Intraday> {

    @Override
    public Intraday deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        JsonNode metadataNode = node.get("Meta Data");
        IntradayMetadata metadata = setMetadata(metadataNode);

        JsonNode timeSeriesNode = node.get(String.format("Time Series (%s)", metadata.getInterval()));
        Map<String, IntradayTimeSeries> timeSeries = new LinkedHashMap<>();
        timeSeriesNode.fields().forEachRemaining(entry -> timeSeries.put(entry.getKey(), setTimeSeries(entry.getValue())));

        Intraday intraday = new Intraday();
        intraday.setMetadata(metadata);
        intraday.setTimeSeries(timeSeries);

        return intraday;
    }

    private IntradayMetadata setMetadata(JsonNode metadataNode) {
        IntradayMetadata metadata = new IntradayMetadata();
        metadata.setInformation(metadataNode.get("1. Information").asText());
        metadata.setSymbol(metadataNode.get("2. Symbol").asText());
        metadata.setLastRefreshed(metadataNode.get("3. Last Refreshed").asText());
        metadata.setInterval(metadataNode.get("4. Interval").asText());
        metadata.setOutputSize(metadataNode.get("5. Output Size").asText());
        metadata.setTimeZone(metadataNode.get("6. Time Zone").asText());
        return metadata;
    }

    private IntradayTimeSeries setTimeSeries(JsonNode seriesNode) {
        IntradayTimeSeries timeSeries = new IntradayTimeSeries();
        timeSeries.setOpen(seriesNode.get("1. open").asText());
        timeSeries.setHigh(seriesNode.get("2. high").asText());
        timeSeries.setLow(seriesNode.get("3. low").asText());
        timeSeries.setClose(seriesNode.get("4. close").asText());
        timeSeries.setVolume(seriesNode.get("5. volume").asText());
        return timeSeries;
    }
}
