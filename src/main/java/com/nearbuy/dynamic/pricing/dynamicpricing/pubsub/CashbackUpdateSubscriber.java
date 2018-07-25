package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.NotificationMongoModel;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashbackUpdateSubscriber implements AppSubscriber<Notification.NotifiactionWrapper>{

    public static final Logger logger = LoggerFactory.getLogger(CashbackUpdateSubscriber.class);

    @Autowired
    NotificationDao notificationDao;

    @Autowired
    BookingDao bookingDao;

    @Override
    public void consume(Notification.NotifiactionWrapper inventory) {
        logger.info(inventory.toString());
        logger.info(inventory.getPayloadData().getInventories().get(0).getUpdatedCashBack()+"");
        if(inventory.getMsg().equalsIgnoreCase("INVENTORY_UPDATE")){
            Double updtaedCb = inventory.getPayloadData().getInventories().get(0).getUpdatedCashBack();
            Long inventoryId = inventory.getPayloadData().getInventories().get(0).getInventoryId();
            if(notificationDao.getNotificationById(inventoryId)!=null){
            NotificationMongoModel mongoModel = notificationDao.getNotificationById(inventoryId);
                bookingDao.update(mongoModel.getMerchantid(),updtaedCb);
            }
            else{
                logger.info("Not our Action :");
            }
        }

    }

    @Override
    public Class getClazz() {
        return Notification.NotifiactionWrapper.class;
    }
}
