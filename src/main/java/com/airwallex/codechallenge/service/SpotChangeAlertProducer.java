package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.Alert;
import com.airwallex.codechallenge.input.AlertType;
import com.airwallex.codechallenge.input.CurrencyConversionRate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpotChangeAlertProducer implements AlertProducer {

    @Override
    public List<Alert> produceAlerts(List<CurrencyConversionRate> conversionRates) {
        return conversionRates.stream()
                .filter(cr -> isAlarmingSpotChange(conversionRates.stream(), cr))
                .map(cr -> new Alert(cr.getTimestamp(), cr.getCurrencyPair(), AlertType.spotChange, null))
                .collect(Collectors.toList());
    }

    private boolean isAlarmingSpotChange(Stream<CurrencyConversionRate> conversionRates, CurrencyConversionRate currencyConversionRate) {
        CurrencyConversionRate minRate = conversionRates
                .filter(cr -> cr.getTimestamp().minusSeconds(300).isBefore(currencyConversionRate.getTimestamp())
                        && cr.getTimestamp().plusSeconds(300).isAfter(currencyConversionRate.getTimestamp()))
                .min(Comparator.comparingDouble(CurrencyConversionRate::getRate)).get();

        return Math.abs(currencyConversionRate.getRate() - minRate.getRate()) * 100 / minRate.getRate() > 1.1;
    }
}
