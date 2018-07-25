package com.nearbuy.dynamic.pricing.dynamicpricing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamicPricingApplication {

    private static Logger logger = LoggerFactory.getLogger(DynamicPricingApplication.class);
	public static void main(String[] args) {
		//System.setProperty("spring.profiles.default", "STAG");
        logger.info("Application is up");
		SpringApplication.run(DynamicPricingApplication.class, args);
	}
}
