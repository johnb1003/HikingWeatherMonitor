package com.johnburnsdev.hikingweathermonitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnburnsdev.hikingweathermonitor.util.ConfigUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;

@Log
@SpringBootApplication
@RequiredArgsConstructor
public class HikingWeatherMonitorApplication {

	public static final String WASHINGTON_URL_KEY = "washington.forecastURL";

	private final ConfigUtil configUtil;

	public static void main(String[] args) {
		SpringApplication.run(HikingWeatherMonitorApplication.class, args);
	}

	@Bean
	public WebClient washingtonWebClient() {
		return WebClient.builder()
				.baseUrl(configUtil.getProperty(WASHINGTON_URL_KEY))
				.codecs(configurer -> {
					// register a custom decoder to deserialize JSON with content type text/plain
					ObjectMapper objectMapper = configurer.getReaders().stream()
							.filter(reader -> reader instanceof Jackson2JsonDecoder)
							.map(Jackson2JsonDecoder.class::cast)
							.map(Jackson2JsonDecoder::getObjectMapper)
							.findFirst()
							.orElseGet(() -> Jackson2ObjectMapperBuilder.json().build());

					Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(objectMapper, MediaType.TEXT_PLAIN);
					configurer.customCodecs().registerWithDefaultConfig(decoder);
				})
				.build();
	}
}
