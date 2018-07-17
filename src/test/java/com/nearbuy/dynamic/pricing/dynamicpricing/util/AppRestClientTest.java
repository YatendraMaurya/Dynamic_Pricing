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

import static com.nearbuy.dynamic.pricing.dynamicpricing.Config.CacheManager.map;

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

    DiscoveryPostRequest discoveryPostRequest=new DiscoveryPostRequest("10000001781",Long.valueOf(50),"1530037800000","1530037800000",28.4436064,77.1000444);


    @Test
    public void PostRequestTest() {
        MerchantDiscoveryResponse merchantDiscoveryResponse =Dservice.getDiscoveryDetail(discoveryPostRequest);
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
        logger.info(bookingResponse.getBooking().getBookingInitiatedAt()+"");
        logger.info(bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getDate());
        logger.info(bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getTimeSlot()+"");
        logger.info(bookingResponse.getOrderDetail().getCustomerId()+"");
    }

    @Test
    public void MerchantTest() {
        MerchantDetail merchantDetail=merchantService.getMerchant(63175L);
        logger.info(merchantDetail.getAddress().getLatitude()+" "+merchantDetail.getAddress().getLongitude()+"");
        logger.info(map.size()+"");
        //Assert.assertFalse(mids.isEmpty());
    }

    @Autowired
    BookingDao bookingDao;

    @Test
    public void Mongotest(){
        bookingDao.getBookingbyOrderId("5b4c73849cef6163d7a2886a");
    }

}
