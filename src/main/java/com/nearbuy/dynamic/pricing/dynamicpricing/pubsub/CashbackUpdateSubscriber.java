package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class CashbackUpdateSubscriber implements AppSubscriber{

    @Override
    public void consume(Object o) {

    }

    @Override
    public Class getClazz() {
        return Notification.class;
    }
}
