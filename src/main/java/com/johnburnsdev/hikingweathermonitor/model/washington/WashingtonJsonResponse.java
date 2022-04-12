package com.johnburnsdev.hikingweathermonitor.model.washington;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WashingtonJsonResponse {

    @JsonProperty("SummitOutlook")
    private WashingtonSummitOutlook summitOutlook;
}
