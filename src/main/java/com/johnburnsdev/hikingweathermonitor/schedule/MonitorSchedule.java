package com.johnburnsdev.hikingweathermonitor.schedule;

import com.johnburnsdev.hikingweathermonitor.client.TwilioClient;
import com.johnburnsdev.hikingweathermonitor.service.ForecastService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@EnableScheduling
@Configuration
public class MonitorSchedule {

    private static final String ALERT_HEADER = "Good hiking weather inbound:\n\n";

    @Autowired
    private List<ForecastService> forecastServices;

    @Autowired
    private TwilioClient twilioClient;

    //@Scheduled(cron = "0 0 10 * * ?")
    @Scheduled(cron = "*/30 * * * * ?")
    public void monitorMountainForecasts() {
        log.info("Fetching mountain forecasts.");

        String alertString = forecastServices.stream()
                .map(ForecastService::getForecastAlertString)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining("\n\n"))
                .trim();

        if (!alertString.isBlank()) {
            alertString = ALERT_HEADER + alertString;
            twilioClient.sendTextAlert(alertString);
            log.info("Good weather incoming! The following alerts were successfully published: \n"+alertString+"\n");
        }
        else {
            log.info("No alerts to publish...");
        }
        log.info("Going back to sleep!");
    }
}
