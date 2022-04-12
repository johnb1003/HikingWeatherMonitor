package com.johnburnsdev.hikingweathermonitor.service;

import com.johnburnsdev.hikingweathermonitor.client.WashingtonClient;
import com.johnburnsdev.hikingweathermonitor.schedule.MonitorSchedule;
import com.johnburnsdev.hikingweathermonitor.service.WashingtonForecastService;
import com.johnburnsdev.hikingweathermonitor.util.ConfigUtil;
import com.johnburnsdev.hikingweathermonitor.util.TestUtil;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@SpringBootTest("WashingtonForecastServiceTest.Config")
class WashingtonForecastServiceTest {

	@Autowired
	private ConfigUtil configUtil;

	private WashingtonClient washingtonClient;
	private WashingtonForecastService washingtonForecastService;

	@BeforeEach
	private void beforeEach() {
		washingtonClient = Mockito.mock(WashingtonClient.class);
		washingtonForecastService = new WashingtonForecastService(configUtil, washingtonClient);
	}

	@Test
	void unhikableForecastTest() {
		Mockito.when(washingtonClient.getForecast())
				.thenReturn(TestUtil.createWashingtonJsonResponse(0));

		Optional<String> alertString = washingtonForecastService.getForecastAlertString();
		assertThat(alertString.isEmpty(), is(true));
	}

	@Test
	void hikableForecastTest() {
		int numHikableDays = 1;
		Mockito.when(washingtonClient.getForecast())
				.thenReturn(TestUtil.createWashingtonJsonResponse(numHikableDays));

		Optional<String> alertString = washingtonForecastService.getForecastAlertString();
		assertThat(alertString.isPresent(), is(true));
		assertThat(StringUtils.countMatches(alertString.get(), "FORECAST"), is(numHikableDays));
	}

	@Nested
	@Configuration
	public class Config {

		@Bean
		public ConfigUtil configUtil(ApplicationContext context) {
			return new ConfigUtil(context.getEnvironment());
		}

		@Bean
		public WashingtonClient washingtonClient() {
			return Mockito.mock(WashingtonClient.class);
		}

		@Bean
		public WashingtonForecastService washingtonForecastService(ConfigUtil configUtil, WashingtonClient washingtonClient) {
			return new WashingtonForecastService(configUtil, washingtonClient);
		}
	}
}
