package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.Alert;
import com.airwallex.codechallenge.input.AlertType;
import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.service.builder.CurrencyConversionRateBuilder;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LongTermAlertProducerTest {

    private LongTermAlertProducer longTermAlertProducer = new LongTermAlertProducer();

    @Test
    public void shouldAlertRaiseAndThrottle() {
        Instant time = Instant.parse("2019-09-15T10:15:30.00Z");
        List<CurrencyConversionRate> mockRates = new ArrayList<>();
        for(int i = 0; i < 60 * 16; i++) {
            mockRates.add(new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(11.0 + i/100.0).withTimestamp(time.plusSeconds(i)).build());
        }

        List<Alert> result = longTermAlertProducer.produceAlerts(mockRates);

        assertEquals(17, result.size());
        assertEquals(16, result.stream().filter(a -> a.getAlert().equals(AlertType.spotChange)).count());
        assertEquals(1, result.stream().filter(a -> a.getAlert().equals(AlertType.raising)).count());
        assertFalse(result.stream().filter(a -> a.getAlert().equals(AlertType.falling)).findFirst().isPresent());
    }

    @Test
    public void shouldAlertFallAndThrottle() {
        Instant time = Instant.parse("2019-09-15T10:15:30.00Z");
        List<CurrencyConversionRate> mockRates = new ArrayList<>();
        for(int i = 0; i < 60 * 16; i++) {
            mockRates.add(new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(11.0 - i/100.0).withTimestamp(time.plusSeconds(i)).build());
        }

        List<Alert> result = longTermAlertProducer.produceAlerts(mockRates);

        assertEquals(17, result.size());
        assertEquals(16, result.stream().filter(a -> a.getAlert().equals(AlertType.spotChange)).count());
        assertEquals(1, result.stream().filter(a -> a.getAlert().equals(AlertType.falling)).count());
        assertFalse(result.stream().filter(a -> a.getAlert().equals(AlertType.raising)).findFirst().isPresent());
    }
}
