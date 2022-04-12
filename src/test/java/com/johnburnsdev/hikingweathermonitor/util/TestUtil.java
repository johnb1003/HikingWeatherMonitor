package com.johnburnsdev.hikingweathermonitor.util;

import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonForecast;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonImperialForecast;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonJsonResponse;
import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonSummitOutlook;

public class TestUtil {

    private static final String PERIOD = "Test period";
    private static final String SYNOPSIS = "Test synopsis";
    private static final String TEMPERATURE = "Test temperature";
    private static final String HIKABLE_WIND = "W shifting NW at 20-35 mph increasing to 35-45 mph w/ gusts up to 50 mph";
    private static final String UNHIKABLE_WIND = "Test wind";
    private static final String WIND_CHILL = "Test wind chill";


    public static WashingtonJsonResponse createWashingtonJsonResponse(int numHikableForecasts) {
        WashingtonJsonResponse response = new WashingtonJsonResponse();
        WashingtonSummitOutlook summitOutlook = new WashingtonSummitOutlook();

        summitOutlook.setForecast1(createWashingtonForecast(numHikableForecasts >= 1 ? HIKABLE_WIND : UNHIKABLE_WIND));
        summitOutlook.setForecast2(createWashingtonForecast(numHikableForecasts >= 2 ? HIKABLE_WIND : UNHIKABLE_WIND));
        summitOutlook.setForecast3(createWashingtonForecast(numHikableForecasts >= 3 ? HIKABLE_WIND : UNHIKABLE_WIND));
        summitOutlook.setForecast4(createWashingtonForecast(numHikableForecasts >= 4 ? HIKABLE_WIND : UNHIKABLE_WIND));

        response.setSummitOutlook(summitOutlook);
        return response;
    }

    private static WashingtonForecast createWashingtonForecast(String wind) {
        WashingtonForecast forecast = new WashingtonForecast();

        forecast.setPeriod(PERIOD);
        forecast.setSynopsis(SYNOPSIS);
        forecast.setImperialForecast(createWashingtonImperialForecast(wind));
        return forecast;
    }

    private static WashingtonImperialForecast createWashingtonImperialForecast(String wind) {
        WashingtonImperialForecast imperialForecast = new WashingtonImperialForecast();
        imperialForecast.setTemperature(TEMPERATURE);
        imperialForecast.setWind(wind);
        imperialForecast.setWindChill(WIND_CHILL);
        return imperialForecast;
    }
}
