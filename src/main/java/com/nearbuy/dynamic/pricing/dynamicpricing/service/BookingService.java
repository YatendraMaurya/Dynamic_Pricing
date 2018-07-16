package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nearbuy.dynamic.pricing.dynamicpricing.factory.AppRequestFactory;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AppRestResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.print.Book;

@Service
public class BookingService {


    private static final Logger logger=LoggerFactory.getLogger(BookingService.class);
    private static String BOOKING_SERVICE="http://walle.nearbuytoolsstag.in/v2/bookings/order/";

    @Autowired
    private AppProperties env;

    @Autowired
    private AppRestClient client;

    public BookingResponse getBookingDetails(long id){
        String url = BOOKING_SERVICE+id+"?isDetailReq=true";
        logger.info(url);
        ResponseEntity<String> resp = client.fireGet(url,null,null);
        if (resp.getStatusCode().is2xxSuccessful()) {
            logger.info(AppUtil.getFromJson(resp.getBody(), BookingResponse.class).toString());
            return AppUtil.getFromJson(resp.getBody(), BookingResponse.class);
        } else {
            logger.error("Error in getting booking Details for bookingId {}",id);
            return null;
        }
    }


}
