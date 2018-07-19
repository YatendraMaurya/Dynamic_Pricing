package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashbackUpdateSubscriber implements AppSubscriber<Notification.NotifiactionWrapper>{

    @Autowired
    NotificationDao notificationDao;
    @Override
    public void consume(Notification.NotifiactionWrapper inventory) {
        if(inventory.getMsg().equalsIgnoreCase("INVENTORY_UPDATE")){

        }

    }

    @Override
    public Class getClazz() {
        return Notification.NotifiactionWrapper.class;
    }
}
