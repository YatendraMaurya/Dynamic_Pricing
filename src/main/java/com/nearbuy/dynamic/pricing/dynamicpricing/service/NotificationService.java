package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.google.gson.reflect.TypeToken;
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


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String COMMUNICATION_NAME = "%COMMUNICATION_NAME%";
    private static final String PLACEHOLDER_MAP = "%PLACEHOLDER_MAP%";
    private static final String TEMPLATE_ID = "%TEMPLATE_ID%";
    private static final String CUSTOMER_ID = "%CUSTOMER_ID";
    private static final String COMMUNICATION_TYPE = "%COMMUNICATION_TYPE%";
    private static final String DOES_DND_APPLIES = "%DOES_DND_APPLIES%";
    private static final String GENERIC_LIST = "generic-list";
    /**
     * 1, 2, 3 => high medium low
     */
    private static final String MULTICHANNEL_COM_MEDIUM_HIGH = "%medium1%";
    private static final String MULTICHANNEL_COM_MEDIUM_LOW = "%medium2%";
    private static final String MULTICHANNEL_COMMUNICATION_MEDIUM = "["+MULTICHANNEL_COM_MEDIUM_HIGH+"],["+MULTICHANNEL_COM_MEDIUM_LOW+"]";
    private static final String PRIORITY = "%PRIORITY%";
    private static final String EXPIRES_AT = "%EXPIRES_AT%";
    private static final String UTM_TERM = "%UTM_TERM%";
    private static final String IN_APP_TRACKING = "%IN_APP_TRACKING%";
    private static final String UTM_SOURCE = "%UTM_SOURCE%";
    private static final String CAMPAIGN_ID = "%CAM_ID%";
    private static final String INTERNAL_CAMPAIGN_NAME = "%INTERNAL_CAMPAIGN_NAME%";
    private static final String IN_APP_LABEL = "%IN_APP_LABEL%";
    private static final String PRIORITY_FIELD = "  \"priority\": " + PRIORITY + ",";
    private static final String COMMUNICATION_MEDIUM = "%COMM_MEDIUM%";
    private static final String IN_APP_UTM =
            "  \"category\": "+"\"notifications-cerebro\","+
                    "  \"label\": "+"\""+ IN_APP_LABEL +"\","+
                    "  \"internalCampaignName\": "+"\""+ INTERNAL_CAMPAIGN_NAME +"\",";
    private static final String DEFAULT_PAYLOAD = "{"+
            "  \"communicationMedium\": ["+COMMUNICATION_MEDIUM+""+
            "  ],"+
            "  \"communicationName\": "+"\""+ COMMUNICATION_NAME +"\", "+
//            "  \"utmTerm\": "+"\""+ UTM_TERM +"\","+
            IN_APP_TRACKING+
            "  \"campaignId\": "+"\""+ CAMPAIGN_ID +"\","+
            "  \"enforceDndLimits\": "+"\""+ DOES_DND_APPLIES +"\","+
            "  \"expiresAt\": "+EXPIRES_AT+","+
            PRIORITY_FIELD+
            "  \"utmSource\": \""+UTM_SOURCE+"\","+
            "  \"communicationType\": "+COMMUNICATION_TYPE+","+
            "  \"createdBy\": \"CEREBRO\","+
            "  \"templateCommunicationVO\": {"+
            "    \"placeHolderMap\": "+PLACEHOLDER_MAP+""+
            ","+
            "    \"templateId\": "+TEMPLATE_ID+""+
            "  },"+
            "  \"userCommunication\": {"+
            "    \"userType\": 2,"+
            "    \"users\": ["+ CUSTOMER_ID+"]"+
            "  }"+
            "}";

    private static final String url = "http://nbdeliverymanager.nearbuytoolsstag.in/api/v1/communication";

    public Long send(){
        return null;
    }

    public Integer getNotificationStatus(Long id) {
        String uri = "http://nbdeliverymanager.nearbuytoolsstag.in/api/v1/communication/"+id+"/stats";
        ResponseEntity<String> response = restClient.fireGet(uri, null, null);
        logger.info("%%%%%%%%%%%%"+response.getStatusCode().toString());
        logger.info(response.getBody().toString());
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("exception from delivery manager in fetching notif open status for id " + id);
        }else{
            logger.info(response.getBody());
//            [{"lifeCycleStatus":80,"total":1}]
//            List<Map> res = AppUtil.parseJson(response.getBody(), new TypeToken<List<Map>>() {
//            }.getType());
//            int status = (int) Double.parseDouble(res.get(0).get("lifeCycleStatus").toString());
//            if(status==OPENED) return AppConstants.NOTIFICATION_STATUS.OPENED;
//            if(status==FAILED) return AppConstants.NOTIFICATION_STATUS.FAILED;
//            if(status==DELIVERED1 || status==DELIVERED2) return AppConstants.NOTIFICATION_STATUS.SUCCESS;
//            return null;
            return 1;
        }
    }

}
