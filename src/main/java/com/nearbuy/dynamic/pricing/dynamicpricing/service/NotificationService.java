package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private static final int OPENED = 60;
    private static final int FAILED = 80;
    private static final int DELIVERED1 = 40;
    private static final int DELIVERED2 = 30;

    @Autowired
    AppRestClient restClient;

    @Autowired
    NotificationDao notificationDao;

    @Autowired
    AppProperties env;


    private String postBody = "{\n" +
            "  \"communicationName\": \"dynamicCashbackForMerchant\",\n" +
            "  \"communicationType\": 1,\n" +
            "  \"communicationMedium\": [\n" +
            "    2\n" +
            "  ],\n" +
            "  \"createdBy\": \"DYNAMIC_PRICING\",\n" +
            "  \"templateCommunicationVO\": {\n" +
            "    \"templateId\": " + TEMPLATE_ID + ",\n" +
            "    \"placeHolderMap\": {\n" +
            "      \"CTA\": \"sdfghj\",\n" +
            "      \"MID\": \"" + MID + "\",\n" +
            "      \"OID\": \"" + OPTION_ID + "\",\n" +
            "      \"CB_TO\": \"" + CASHBACK_TO + "\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"userCommunication\": {\n" +
            "    \"userType\": 1,\n" +
            "    \"users\": [\n" +
            "      " + USERS +
            "    ]\n" +
            "  },\n" +
            "  \"utmSource\": \"sonar\",\n" +
            "  \"trimPushNotification\": true\n" +
            "}";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int MID = -1;
    private static final int CASHBACK_TO = -2;
    private static final int OPTION_ID = -3;
    private static final String USERS = "%USERS%";
    private static final String TEMPLATE_ID = "%TEMPLATE_ID%";

    public Long send(Long mid,List<Long> users, int templateId, Double cashback_from, Double cashback_to,Long optioinID){
        String url = env.getProperty("notification.manager.base");
        logger.info(String.valueOf(cashback_to));
        logger.info(url);
        StringBuilder userArray = new StringBuilder();
        for(Long num : users){
            userArray.append("\"").append(num).append("\"").append(',');
        }
        userArray.deleteCharAt(userArray.length() - 1);
        String userArr = userArray.toString();
        this.postBody = this.postBody.replace(TEMPLATE_ID, String.valueOf(templateId)).
                replace(String.valueOf(MID), String.valueOf(mid)).
                replace(String.valueOf(OPTION_ID), String.valueOf(optioinID)).
                replace(String.valueOf(CASHBACK_TO), String.valueOf(cashback_to)).replace(USERS, userArr);
        logger.info(postBody);
        ResponseEntity<String> res=restClient.firePostJson(url,null,postBody);
        if (res.getStatusCode().is2xxSuccessful()) {
            logger.info(AppUtil.getFromJson(res.getBody(), Long.class).toString());
            logger.info("Successfully sent notification");
            return AppUtil.getFromJson(res.getBody(), Long.class);
        } else {
            logger.error("Cound not Send Notification");
            return null;
        }
    }

}