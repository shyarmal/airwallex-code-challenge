package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.Reader;
import com.airwallex.codechallenge.service.AlertingService;
import static com.airwallex.codechallenge.service.util.JsonUtil.writeAsString;

import java.util.stream.Collectors;

public class App {

    private static AlertingService alertingService = new AlertingService();

    public static void main(String[] args) {
        Reader reader = new Reader();

        reader.read(args[0])
                .collect(Collectors.groupingBy(c -> c.getCurrencyPair()))
                .entrySet().stream()
                .map(c -> alertingService.produceAlert(c.getValue()))
                .forEach(currencyConversionRate -> System.out.println(writeAsString(currencyConversionRate)));

    }

}
