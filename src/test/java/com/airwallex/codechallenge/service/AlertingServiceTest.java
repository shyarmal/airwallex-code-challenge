package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.CurrencyConversionRate;

import com.airwallex.codechallenge.service.builder.CurrencyConversionRateBuilder;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlertingServiceTest {

    private AlertingService alertingService = new AlertingService();

    @Test
    public void shouldAlertSpotChangeForCurrencyPair() throws NoSuchFieldException, IllegalAccessException {
        Clock clock = Clock.systemUTC();

        List<CurrencyConversionRate> mockRates = Arrays.asList(
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.001).withTimestamp(clock.instant().plusSeconds(1)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.001).withTimestamp(clock.instant().plusSeconds(2)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.001).withTimestamp(clock.instant().plusSeconds(3)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.003).withTimestamp(clock.instant().plusSeconds(4)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.002).withTimestamp(clock.instant().plusSeconds(5)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.008).withTimestamp(clock.instant().plusSeconds(6)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.011).withTimestamp(clock.instant().plusSeconds(7)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.010).withTimestamp(clock.instant().plusSeconds(8)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.010).withTimestamp(clock.instant().plusSeconds(9)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.010).withTimestamp(clock.instant().plusSeconds(10)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.013).withTimestamp(clock.instant().plusSeconds(11)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.001).withTimestamp(clock.instant().plusSeconds(12)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.008).withTimestamp(clock.instant().plusSeconds(13)).build()
        );


        alertingService.produceAlert(mockRates);

        assertTrue(getAlertProducer() instanceof SpotChangeAlertProducer);
    }

    @Test
    public void shouldAlertRaiseAndThrottle() throws NoSuchFieldException, IllegalAccessException {
        Instant time = Instant.parse("2019-09-15T10:15:30.00Z");
        List<CurrencyConversionRate> mockRates = new ArrayList<>();
        for(int i = 0; i < 60 * 16; i++) {
            mockRates.add(new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(11.0 + i/100.0).withTimestamp(time.plusSeconds(i)).build());
        }

        alertingService.produceAlert(mockRates);

        assertTrue(getAlertProducer() instanceof LongTermAlertProducer);
    }

    private AlertProducer getAlertProducer() throws NoSuchFieldException, IllegalAccessException {
        Field alertProducerField = alertingService.getClass().getDeclaredField("alertProducer");
        alertProducerField.setAccessible(true);
        return (AlertProducer) alertProducerField.get(alertingService);
    }

}
