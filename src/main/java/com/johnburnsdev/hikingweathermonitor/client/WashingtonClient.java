package com.johnburnsdev.hikingweathermonitor.client;

import com.johnburnsdev.hikingweathermonitor.model.washington.WashingtonJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WashingtonClient {

    @Autowired
    private WebClient washingtonWebClient;

    public WashingtonJsonResponse getForecast() {
        return washingtonWebClient.get()
                .retrieve()
                .bodyToMono(WashingtonJsonResponse.class)
                .block();
    }
}
