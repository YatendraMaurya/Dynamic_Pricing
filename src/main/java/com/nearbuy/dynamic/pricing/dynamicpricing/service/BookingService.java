package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.nearbuy.dynamic.pricing.dynamicpricing.factory.AppRequestFactory;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.print.Book;

@Service
public class BookingService {

    private static final String BOOKING_SERVICE = "booking.service";

    @Autowired
    private AppProperties env;

    @Autowired
    private AppRestClient client;

    public BookingResponse getBooking(long id){
        String url = env.getProperty(BOOKING_SERVICE) + "/booking/"+id;
        AppRestResponse resp = client.fireGet(AppRequestFactory.get(url, BookingResponse.class));
        return null;
    }


}
