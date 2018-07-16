package com.nearbuy.dynamic.pricing.dynamicpricing.dao.codec;

import com.nearbuy.dynamic.pricing.model.Notification;
import org.bson.Document;

public class NotifictionCodec extends AppCodec<Notification>{
    @Override
    protected Notification _decode(Document doc) {
        return null;
    }

    @Override
    protected Document _encode(Notification notification) {
        return null;
    }
}
