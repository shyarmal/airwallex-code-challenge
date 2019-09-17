package com.airwallex.codechallenge.service;

import com.airwallex.codechallenge.input.Alert;
import com.airwallex.codechallenge.input.CurrencyConversionRate;

import java.util.List;

public interface AlertProducer {

    public List<Alert> produceAlerts(List<CurrencyConversionRate> conversionRates);
}
