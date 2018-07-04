package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.BookingService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.MerchantService;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingSubscriber implements AppSubscriber<Booking>{

    @Autowired
    BookingService bookingService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    BookingDao bookingDao;


    @Override
    public void consume(Booking o) {

    }

    @Override
    public Class getClazz() {
        return Booking.class;
    }
}
