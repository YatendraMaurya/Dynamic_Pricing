package com.nearbuy.dynamic.pricing.dynamicpricing;

import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class AppConfig {

    public static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    public static boolean isTest = false;

    @Bean
    public AppProperties getProperty() throws IOException {
        String profile = System.getProperty("spring.profiles.active");
        logger.info("picking up profile : {}", profile);
        if (profile == null) {
            logger.error("Assuming environment to be test.");
            profile = "LOCAL";
            isTest = true;
        }
        InputStream stream = getClass().getClassLoader().getResourceAsStream("env-" + profile + ".properties");
//        InputStream stream = getClass().getClassLoader().getResourceAsStream("env-" + profile + ".properties");
        AppProperties prop = new AppProperties();
        prop.load(stream);
        stream.close();
        return prop;
    }
}
