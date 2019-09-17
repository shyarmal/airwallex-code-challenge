package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.Alert;
import com.airwallex.codechallenge.input.AlertType;
import com.airwallex.codechallenge.input.CurrencyConversionRate;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class LongTermAlertProducer extends SpotChangeAlertProducer {

    @Override
    public List<Alert> produceAlerts(List<CurrencyConversionRate> conversionRates) {
        List<Alert> alerts = throttleAlerts(super.produceAlerts(conversionRates));

        Comparator<CurrencyConversionRate> rateComparator = Comparator.comparingDouble(CurrencyConversionRate::getRate);
        Comparator<CurrencyConversionRate> timestampComparator = Comparator.comparing(CurrencyConversionRate::getTimestamp);

        Set<CurrencyConversionRate> conversionRateSet = withoutDuplicates(conversionRates, rateComparator);

        alerts.add(alertFromConversionRateSet(conversionRateSet, timestampComparator));
        return alerts;
    }

    private List<Alert> throttleAlerts(List<Alert> alerts) {
        return alerts.stream().filter(a -> a.getTimestamp().getEpochSecond() % 60 == 0).collect(Collectors.toList());

    }

    private Set<CurrencyConversionRate> withoutDuplicates(List<CurrencyConversionRate> rateList, Comparator<CurrencyConversionRate> comparator) {
        SortedSet<CurrencyConversionRate> rateSet = new TreeSet<>(comparator);
        rateSet.addAll(rateList);
        return rateSet;
    }

    private Alert alertFromConversionRateSet(Set<CurrencyConversionRate> conversionRates, Comparator<CurrencyConversionRate> comparator) {
        CurrencyConversionRate maxTimestampRate = conversionRates.stream().max(comparator).get();
        CurrencyConversionRate minTimestampRate = conversionRates.stream().min(comparator).get();

        long seconds = Duration.between(minTimestampRate.getTimestamp(), maxTimestampRate.getTimestamp()).getSeconds();
        AlertType alertType = (maxTimestampRate.getRate() > minTimestampRate.getRate())? AlertType.raising : AlertType.falling;

        return new Alert(maxTimestampRate.getTimestamp(), maxTimestampRate.getCurrencyPair(), alertType, (int) seconds);
    }
}
