package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
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
    @Override
    public void consume(Notification.NotifiactionWrapper inventory) {
        logger.info(inventory.toString());
        logger.info(inventory.getPayloadData().getInventories().get(0).getUpdatedCashBack()+"");
        if(inventory.getMsg().equalsIgnoreCase("INVENTORY_UPDATE")){
            return;
        }

    }

    @Override
    public Class getClazz() {
        return Notification.NotifiactionWrapper.class;
    }
}
