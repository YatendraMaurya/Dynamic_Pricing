package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.google.gson.reflect.TypeToken;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private final String url = "http://nbdeliverymanager.nearbuytoolsstag.in/api/v1/communication";

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
            "      \"CASHBACK_FROM\": \"" + CASHBACK_FROM + "\",\n" +
            "      \"CASHBACK_TO\": \"" + CASHBACK_TO + "\"\n" +
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
    private static final int CASHBACK_FROM = -1;
    private static final int CASHBACK_TO = -2;
    private static final String USERS = "%USERS%";
    private static final String TEMPLATE_ID = "%TEMPLATE_ID%";

    public Long send(Long mid,List<Long> users, int templateId, Double cashback_from, Double cashback_to,Long optioinID){
        StringBuilder userArray = new StringBuilder();
        for(Long num : users){
            userArray.append("\"").append(num).append("\"").append(',');
        }
        userArray.deleteCharAt(userArray.length() - 1);
        String userArr = userArray.toString();
        this.postBody = this.postBody.replace(TEMPLATE_ID, String.valueOf(templateId)).
                replace(String.valueOf(CASHBACK_FROM), String.valueOf(cashback_from)).
                replace(String.valueOf(CASHBACK_TO), String.valueOf(cashback_to)).replace(USERS, userArr);
        logger.info(postBody);
        ResponseEntity<String> res=restClient.firePostJson(url,null,postBody);
        if (res.getStatusCode().is2xxSuccessful()) {
            logger.info(AppUtil.getFromJson(res.getBody(), Long.class).toString());
            for(Long user:users){
                notificationDao.addNotification(mid,optioinID,user,cashback_from,cashback_to,AppUtil.currentTime(),templateId);
                logger.info("Successfully added");
            }
            return AppUtil.getFromJson(res.getBody(), Long.class);
        } else {
            logger.error("Cound not Send Notification");
            return null;
        }
    }

}