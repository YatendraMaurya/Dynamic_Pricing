package com.nearbuy.dynamic.pricing.dynamicpricing.dao.codec;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.NotificationMongoModel;
import org.bson.Document;

public class NotifictionCodec extends AppCodec<NotificationMongoModel>{

    @Override
    protected NotificationMongoModel _decode(Document doc) {
        NotificationMongoModel notificationMongoModel =new NotificationMongoModel();
        notificationMongoModel.setCASH_BACK_FROM(doc.getDouble(NotificationDao.CASH_BACK_FROM));
        notificationMongoModel.setCASH_BACK_TO(doc.getDouble(NotificationDao.CASH_BACK_TO));
        notificationMongoModel.setTEMPLATE_ID(doc.getInteger(NotificationDao.TEMPLATE_ID));
        notificationMongoModel.setTIMESLOT(doc.getLong(NotificationDao.TIMESLOT));
        notificationMongoModel.setUSER_ID(doc.getLong(NotificationDao.USER_ID));
        notificationMongoModel.setMerchantid(doc.getLong(NotificationDao.MERCHANT_ID));
        return notificationMongoModel;
    }

    @Override
    protected Document _encode(NotificationMongoModel notification) {
        Document doc = new Document();
        doc.put(NotificationDao.USER_ID,notification.getUSER_ID());
        doc.put(NotificationDao.TEMPLATE_ID,notification.getTEMPLATE_ID());
        doc.put(NotificationDao.CASH_BACK_FROM,notification.getCASH_BACK_FROM());
        doc.put(NotificationDao.CASH_BACK_TO,notification.getCASH_BACK_TO());
        doc.put(NotificationDao.MERCHANT_ID,notification.getMerchantid());
        return doc;
    }
}
