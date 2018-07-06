package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppRestClientTest {

    @Autowired
    AppRestClient appRestClient;

    public static final Logger logger = LoggerFactory.getLogger(AppRestClientTest.class);
    String url="http://nilediscovery-unified.iwanto.in/nile-discovery-V2/merchants/listing";
    String type="POST";
    String  body="{  " +
            "   \"offersOnly\":true," +
            "   \"offset\":0," +
            "   \"count\":30," +
            "   \"vertical\":\"LOCAL\"," +
            "   \"categoryIds\":[  " +
            "      \"FNB\"" +
            "   ]," +
            "   \"sortOrder\":\"SCORE_NEAR_BY\"," +
            "   \"sortPattern\":\"ASC\"," +
            "   \"customerId\":10000001781," +
            "   \"workflowTypes\":[  " +
            "      \"BOOKING_TYPE2\"" +
            "   ]," +
            "   \"source\":\"MOBILE\"," +
            "   \"location\":{  " +
            "      \"coordinates\":{  " +
            "         \"lat\":28.4436064," +
            "         \"lng\":77.1000444" +
            "      }," +
            "      \"radius\":50" +
            "   }," +
            "   \"dates\":{  " +
            "      \"startDate\":1530037800000," +
            "      \"endDate\":1530037800000" +
            "   }," +
            "   \"context\":{  " +
            "      \"categoryId\":\"FNB\"" +
            "   }" +
            "}";





    @Test
    public void PostRequestTest()
    {
        AppRestRequest appRestRequest=new AppRestRequest<String,String>(type,url,null,body,String.class);
        logger.info(String.valueOf(appRestRequest.getResponseType() == null));
        AppRestResponse appRestResponse=appRestClient.firePost(appRestRequest);
        logger.info(appRestResponse.getBody().toString());
    }




}
