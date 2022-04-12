package com.johnburnsdev.hikingweathermonitor.service;

import com.johnburnsdev.hikingweathermonitor.client.WashingtonClient;
import com.johnburnsdev.hikingweathermonitor.model.Forecast;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonForecast;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonImperialForecast;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonJsonResponse;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonSummitOutlook;
import com.johnburnsdev.hikingweathermonitor.util.ConfigUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WashingtonForecastService extends ForecastService{

    private static final String MOUNTAIN_NAME = "MT WASHINGTON";
    private static final String EXTRACT_NUMBERS_REGEX = "[^0-9]+";
    private static final String HIKABLE_WIND_THRESHOLD_KEY = "washington.hikableWindThreshold";
    private static final String ALERT_FORECAST_URL = "washington.alertURL";

    private final ConfigUtil configUtil;
    private final WashingtonClient washingtonClient;

    @Override
    public String getMountainName() {
        return MOUNTAIN_NAME;
    }

    @Override
    protected List<Forecast> getForecastList() {
        return convertResponseToForecastList(washingtonClient.getForecast());
    }

    private List<Forecast> convertResponseToForecastList(WashingtonJsonResponse response) {
        return Optional.of(response.getSummitOutlook())
                .map(WashingtonSummitOutlook::getForecastList)
                .orElseThrow(() -> new RuntimeException("Could not parse Summit Outlook"))
                .stream()
                .map(this::convertJsonToForecast)
                .collect(Collectors.toList());
    }

    private Forecast convertJsonToForecast(WashingtonForecast jsonForecast) {
        Forecast forecast = new Forecast();
        forecast.setDay(Optional.of(jsonForecast.getPeriod())
                .orElseThrow(() -> new RuntimeException("Could not parse json day")));
        forecast.setDescription(Optional.of(jsonForecast.getSynopsis())
                .orElseThrow(() -> new RuntimeException("Could not parse json description")));
        forecast.setWind(Optional.of(jsonForecast.getImperialForecast())
                .map(WashingtonImperialForecast::getWind)
                .orElseThrow(() -> new RuntimeException("Could not parse json wind")));
        forecast.setTemperature(Optional.of(jsonForecast.getImperialForecast())
                .map(WashingtonImperialForecast::getTemperature)
                .orElseThrow(() -> new RuntimeException("Could not parse json temperature")));
        return forecast;
    }

    @Override
    protected boolean isForecastHikable(Forecast forecast) {
        List<String> windSpeeds = Arrays.asList(
                        forecast.getWind()
                                .replaceAll(EXTRACT_NUMBERS_REGEX, " ")
                                .split(" ")
                )
                .stream()
                .filter(string -> !string.isBlank())
                .collect(Collectors.toList());

        return !windSpeeds.isEmpty() &&
                windSpeeds.stream()
                .map(Integer::parseInt)
                .allMatch(windSpeed -> windSpeed <= configUtil.getInt(HIKABLE_WIND_THRESHOLD_KEY));
    }

    @Override
    protected String getForecastURL() {
        return configUtil.getProperty(ALERT_FORECAST_URL);
    }
}
