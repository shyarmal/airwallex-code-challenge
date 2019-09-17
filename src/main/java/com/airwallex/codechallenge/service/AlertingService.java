package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.Alert;
import com.airwallex.codechallenge.input.AlertType;
import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlertingService {

    private AlertProducer alertProducer;

    public List<Alert> produceAlert(List<CurrencyConversionRate> conversionRates) {
        alertProducer = (hasBeenChangingFor15Minutes(conversionRates))? new LongTermAlertProducer() : new SpotChangeAlertProducer();
        return alertProducer.produceAlerts(conversionRates);

    }

    private boolean hasBeenChangingFor15Minutes(List<CurrencyConversionRate> conversionRates) {
        Instant maxTimeStamp = conversionRates.stream().max(Comparator.comparing(CurrencyConversionRate::getTimestamp)).get().getTimestamp();
        Instant minTimeStamp = conversionRates.stream().min(Comparator.comparing(CurrencyConversionRate::getTimestamp)).get().getTimestamp();

        return Duration.between(minTimeStamp, maxTimeStamp).toMinutes() >= 15;
    }

}
