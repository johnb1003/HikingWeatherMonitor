package com.johnburnsdev.hikingweathermonitor.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
@RequiredArgsConstructor
public class ConfigUtil {

    private final Environment environment;

    public String getProperty(String key) {
        return environment.getProperty(key);
    }

    public int getInt(String key) {
        return environment.getProperty(key, Integer.class).intValue();
    }

    public String getEnvironmentVariable(String key) {
        return System.getenv(key);
    }
}
