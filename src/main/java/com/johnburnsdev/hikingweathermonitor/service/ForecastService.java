package com.johnburnsdev.hikingweathermonitor.service;

import com.johnburnsdev.hikingweathermonitor.model.Forecast;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ForecastService {

    public Optional<String> getForecastAlertString() {
        List<String> alertStrings = getForecastList().stream()
                .filter(this::isForecastHikable)
                .map(Forecast::toString)
                .collect(Collectors.toList());

        return alertStrings.isEmpty() ?
                Optional.empty() :
                Optional.of(getMountainName() + "\n" + String.join("\n", alertStrings));
    }

    public abstract String getMountainName();

    protected abstract List<Forecast> getForecastList();

    protected abstract boolean isForecastHikable(Forecast forecast);

    protected abstract String getForecastURL();
}
