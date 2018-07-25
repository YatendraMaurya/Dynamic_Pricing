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
        notificationMongoModel.setOptionid(doc.getLong(NotificationDao.OPTION_ID));
        notificationMongoModel.setINVENTORY_ID(doc.getLong(NotificationDao.INVENTORY_ID));
        notificationMongoModel.setINVENTORY_KEY(doc.getLong(NotificationDao.INVENTORY_KEY));
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
        doc.put(NotificationDao.OPTION_ID,notification.getOptionid());
        doc.put(NotificationDao.INVENTORY_ID,notification.getINVENTORY_ID());
        doc.put(NotificationDao.INVENTORY_KEY,notification.getINVENTORY_KEY());
        doc.put(NotificationDao.TIMESLOT,notification.getTIMESLOT());
        return doc;
    }
}
