package com.johnburnsdev.hikingweathermonitor.model.washington;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class WashingtonSummitOutlook {

    @JsonProperty("Forecast1")
    private WashingtonForecast forecast1;

    @JsonProperty("Forecast2")
    private WashingtonForecast forecast2;

    @JsonProperty("Forecast3")
    private WashingtonForecast forecast3;

    @JsonProperty("Forecast4")
    private WashingtonForecast forecast4;

    public List<WashingtonForecast> getForecastList() {
        return Stream.of(forecast1, forecast2, forecast3, forecast4)
                .filter(Objects::nonNull).
                collect(Collectors.toList());
    }
}
