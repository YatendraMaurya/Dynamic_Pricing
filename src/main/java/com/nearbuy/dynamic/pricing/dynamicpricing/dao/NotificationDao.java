package com.nearbuy.dynamic.pricing.dynamicpricing.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.NotificationMongoModel;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
//sdc
@Component
public class NotificationDao {
    public static final Logger logger=LoggerFactory.getLogger(NotificationDao.class);

    @Autowired
    @Qualifier(value = "Notification_Collection")
    MongoCollection NotificationCollection;

    public static final String USER_ID = "userid";
    public static final String CASH_BACK_TO = "cashbackTo";
    public static final String CASH_BACK_FROM = "cashbcackFrom";
    public static final String TIMESLOT = "timeSlot";
    public static final String TEMPLATE_ID = "templateId";
    public static final String MERCHANT_ID = "merchantId";
    public static final String OPTION_ID = "optionId";
    public static final String INVENTORY_ID = "inventoryId";
    public static final String INVENTORY_KEY = "inventoryKey";

    public void addNotification(Long merchid,Long optionId,Long userid, Double cashback_previous, Double cashback_update,
                                Long time,int templateid,Long inventoryId,Long inventorykey){
        Document doc = new Document(MERCHANT_ID,merchid).append(USER_ID,userid).
                append(CASH_BACK_FROM,cashback_previous).
                append(CASH_BACK_TO,cashback_update).
                append(TIMESLOT,time).append(TEMPLATE_ID,templateid).append(OPTION_ID,optionId).
                append(INVENTORY_ID,inventoryId).append(INVENTORY_KEY,inventorykey);
        NotificationCollection.insertOne(doc);
    }

    public NotificationMongoModel getNotificationById(Long inventoryId){
        if(NotificationCollection.find(Filters.eq(INVENTORY_ID,inventoryId),NotificationMongoModel.class).first() != null) {
            return (NotificationMongoModel) NotificationCollection.find(Filters.eq(INVENTORY_ID, inventoryId), NotificationMongoModel.class).first();
        }
        return null;
    }

    public boolean hasNotifiedRecently(Long mid,Long optionId) {
        return NotificationCollection.find(Filters.and(Filters.eq(OPTION_ID,optionId),Filters.eq(MERCHANT_ID,mid))).first() != null;
    }
}
