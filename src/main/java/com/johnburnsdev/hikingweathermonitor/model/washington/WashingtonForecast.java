package com.johnburnsdev.hikingweathermonitor.model.washington;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WashingtonForecast {

    @JsonProperty("Period")
    private String period;

    @JsonProperty("Synopsis")
    private String synopsis;

    @JsonProperty("Imperial")
    private WashingtonImperialForecast imperialForecast;
}
