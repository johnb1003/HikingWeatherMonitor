package com.johnburnsdev.hikingweathermonitor.model.washington;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WashingtonImperialForecast {

    @JsonProperty("Temperature")
    private String temperature;

    @JsonProperty("Wind")
    private String wind;

    @JsonProperty("WindChill")
    private String windChill;
}
