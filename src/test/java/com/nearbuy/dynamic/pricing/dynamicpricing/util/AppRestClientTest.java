package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.google.gson.Gson;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.BookingService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.DiscoveryService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.MerchantService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppRestClientTest {
    @Autowired
    DiscoveryService Dservice;

    @Autowired
    BookingService service;

    @Autowired
    MerchantService merchantService;

    public static final Logger logger = LoggerFactory.getLogger(AppRestClientTest.class);
    String  body="{  " +
            "   \"offersOnly\":true," +
            "   \"offset\":0," +
            "   \"count\":30," +
            "   \"vertical\":\"LOCAL\"," +
            "   \"categoryIds\":[  " +
            "      \"FNB\"" +
            "   ]," +
            "   \"sortOrder\":\"SCORE_NEAR_BY\"," +
            "   \"sortPattern\":\"ASC\"," +
            "   \"customerId\":10000001781," +
            "   \"workflowTypes\":[  " +
            "      \"BOOKING_TYPE2\"" +
            "   ]," +
            "   \"source\":\"MOBILE\"," +
            "   \"location\":{  " +
            "      \"coordinates\":{  " +
            "         \"lat\":28.4436064," +
            "         \"lng\":77.1000444" +
            "      }," +
            "      \"radius\":50" +
            "   }," +
            "   \"dates\":{  " +
            "      \"startDate\":1530037800000," +
            "      \"endDate\":1530037800000" +
            "   }," +
            "   \"context\":{  " +
            "      \"categoryId\":\"FNB\"" +
            "   }" +
            "}";

    @Test
    public void PostRequestTest() {
        MerchantDiscoveryResponse merchantDiscoveryResponse =Dservice.getDiscoveryDetail(body);
        logger.info(merchantDiscoveryResponse.toString());

    }

    @Test
    public void BookingserviceTest()  {
        BookingResponse bookingResponse=service.getBookingDetails(3826720L);
        ArrayList<Double> cashbacks = new ArrayList<>();
        for(BookingResponse.OrderLine orderLine : bookingResponse.getOrderDetail().getOrderLines()){
            for(BookingResponse.OrderBomBOs orderBomBOs : orderLine.getProductBO().getOrderBomBOs()){
                cashbacks.add(orderBomBOs.getCashback());
            }
        }
        logger.info(cashbacks.toString());
        logger.info(bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getMerchants()[0].getMerchantId()+"");
        logger.info(bookingResponse.toString());
    }

    @Test
    public void MerchantTest() {
        MerchantDetail merchantDetail=merchantService.getMerchant(63175L);
        logger.info(merchantDetail.getAddress().getLatitude()+" "+merchantDetail.getAddress().getLongitude()+"");
        //Assert.assertFalse(mids.isEmpty());
    }

    @Autowired
    BookingDao bookingDao;
    @Test
    public void Mongotest(){
        bookingDao.getBookingbyOrderId("5b4c73849cef6163d7a2886a");
    }
}
