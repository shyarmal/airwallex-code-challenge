package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.Alert;
import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.service.builder.CurrencyConversionRateBuilder;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpotChangeAlertProducerTest {

    private SpotChangeAlertProducer spotChangeAlertProducer = new SpotChangeAlertProducer();

    @Test
    public void shouldAlertSpotChangeForCurrencyPair() {
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


        List<Alert> result = spotChangeAlertProducer.produceAlerts(mockRates);

        assertEquals(1, result.size());
        assertEquals(clock.instant().plusSeconds(11).getEpochSecond(), result.get(0).getTimestamp().getEpochSecond());
    }

    @Test
    public void shouldNotAlert() {
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
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.011).withTimestamp(clock.instant().plusSeconds(11)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.011).withTimestamp(clock.instant().plusSeconds(12)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.008).withTimestamp(clock.instant().plusSeconds(13)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.008).withTimestamp(clock.instant().plusSeconds(14)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.011).withTimestamp(clock.instant().plusSeconds(15)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.010).withTimestamp(clock.instant().plusSeconds(16)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.010).withTimestamp(clock.instant().plusSeconds(17)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.010).withTimestamp(clock.instant().plusSeconds(18)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.011).withTimestamp(clock.instant().plusSeconds(19)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.011).withTimestamp(clock.instant().plusSeconds(20)).build(),
                new CurrencyConversionRateBuilder().withCurrencyPair("CNYAUD").withRate(1.008).withTimestamp(clock.instant().plusSeconds(21)).build()
        );

        List<Alert> result = spotChangeAlertProducer.produceAlerts(mockRates);

        assertEquals(0, result.size());
    }
}
