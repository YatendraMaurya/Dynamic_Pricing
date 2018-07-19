package com.nearbuy.dynamic.pricing.dynamicpricing.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.NotificationMongoModel;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

    public void addNotification(Long merchid,Long userid, Double cashback_previous, Double cashback_update, Long time,int tempid){
        Document doc = new Document(MERCHANT_ID,merchid).append(USER_ID,userid).
                append(CASH_BACK_FROM,cashback_previous).
                append(CASH_BACK_TO,cashback_update).
                append(TIMESLOT,time).append(TEMPLATE_ID,tempid);
        NotificationCollection.insertOne(doc);
    }

    public NotificationMongoModel getNotificationById(Long userId){
        return (NotificationMongoModel) NotificationCollection.find(Filters.eq(USER_ID,userId),NotificationMongoModel.class).first();
    }
}
