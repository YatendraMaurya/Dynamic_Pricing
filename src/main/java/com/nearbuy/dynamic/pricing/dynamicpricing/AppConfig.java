package com.nearbuy.dynamic.pricing.dynamicpricing;

import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

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

    @Bean(name="restOperations")
    public RestTemplate getRestTemplate() throws IOException {
        AppProperties env = getProperty();
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        int TIMEOUT = Integer.parseInt(env.getProperty("request.timeout"));
        f.setConnectTimeout(TIMEOUT);
        f.setReadTimeout(TIMEOUT);
        RestTemplate restOperations = new RestTemplate(f);
        restOperations.setErrorHandler(new DefaultResponseErrorHandler() {

            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
//                if(statusCode.equals(HttpStatus.BAD_REQUEST) || statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                return false;
//                }
//                return super.hasError(response);
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                super.handleError(response);
            }
        });

        return restOperations;
    }
}
