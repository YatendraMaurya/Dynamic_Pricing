package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.model.Booking;

public class BookingSubscriber implements AppSubscriber<Booking>{

    @Override
    public void consume(Booking o) {

    }

    @Override
    public Class getClazz() {
        return Booking.class;
    }
}
