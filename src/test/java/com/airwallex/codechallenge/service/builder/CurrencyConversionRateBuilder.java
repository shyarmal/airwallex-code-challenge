package com.airwallex.codechallenge.service.builder;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.service.AlertingServiceTest;

import java.time.Instant;

public class CurrencyConversionRateBuilder {

    private Instant timestamp;
    private String currencyPair;
    private Double rate;

    public CurrencyConversionRate build() {
        return new CurrencyConversionRate(timestamp, currencyPair, rate);
    }

    public CurrencyConversionRateBuilder withTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public CurrencyConversionRateBuilder withCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public CurrencyConversionRateBuilder withRate(Double rate) {
        this.rate = rate;
        return this;
    }
}
