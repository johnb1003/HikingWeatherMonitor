package com.johnburnsdev.hikingweathermonitor.model;

import lombok.Data;

@Data
public class Forecast {

    private static final String TO_STRING_TEMPLATE = "%s FORECAST\n%s\nTEMPERATURE: %s\nWIND: %s";

    private String day;
    private String description;
    private String temperature;
    private String wind;

    @Override
    public String toString() {
        return String.format(TO_STRING_TEMPLATE, day, description, temperature, wind);
    }
}
