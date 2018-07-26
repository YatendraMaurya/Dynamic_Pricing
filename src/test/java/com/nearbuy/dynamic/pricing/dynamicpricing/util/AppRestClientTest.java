package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.google.gson.Gson;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.NotificationMongoModel;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.*;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    NotificationService notificationService;

    public static final Logger logger = LoggerFactory.getLogger(AppRestClientTest.class);

    DiscoveryPostRequest discoveryPostRequest=new DiscoveryPostRequest("10000001781",Long.valueOf(50),"1530037800000","1530037800000",28.4436064,77.1000444,2);


    @Test
    public void PostRequestTest() {
        MerchantDiscoveryResponse merchantDiscoveryResponse =Dservice.getDiscoveryDetail(discoveryPostRequest);
        logger.info(merchantDiscoveryResponse.toString());
    }

    @Test
    public void BookingserviceTest()  {
        BookingResponse bookingResponse=service.getBookingDetails(3826720l);
        logger.info(bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getOfferValidity().getValidityTimings()[0].getTags()[0]);
        ArrayList<Double> cashbacks = new ArrayList<>();
        for(BookingResponse.OrderLine orderLine : bookingResponse.getOrderDetail().getOrderLines()){
            for(BookingResponse.OrderBomBOs orderBomBOs : orderLine.getProductBO().getOrderBomBOs()){
                cashbacks.add(orderBomBOs.getCashback());
            }
        }
        Double cashb = bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getDiscount().getPercent();
        logger.info(cashb+"");

        logger.info(bookingResponse.getBooking().getOffers()[0].getOfferId()+"");
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
        logger.info(merchantDetail.getRs().getAddress().getLatitude()+" "+merchantDetail.getRs().getAddress().getLongitude()+"");
        logger.info(map.size()+"");
        logger.info(merchantDetail.getRs().getBusinessAccountId()+"");
        //Assert.assertFalse(mids.isEmpty());
    }

    @Autowired
    BookingDao bookingDao;

    @Ignore
    @Test
    public void Mongotest(){
        bookingDao.getBookingbyOrderId("5b4f28539cef617d71266ac9");
    }

    @Autowired
    AccountService accountService;

    @Test
    public void Accounttest(){
        List<Long> res=accountService.getDecisonMaker(1000003l);
        logger.info(res.toString());
    }


    @Test
    public void Notificationtest(){
        List<Long> a=new ArrayList<>();
        a.add(1073599l);
        Long resp = notificationService.send(25795L, a, 260, 20.0, 10.0, 30000000127902L);
        Assert.assertNotNull(resp);
    }

    @Autowired
    NotificationDao notificationDao;

    @Test
    public void NotificationMongo(){
        NotificationMongoModel notificationMongo=notificationDao.getNotificationById(1052571l);
        if(notificationMongo != null)
        logger.info(notificationMongo.toString());
        else
            logger.info("notification not found in Mongo");
    }

    @Autowired
    DealService dealService;

    @Test
    public void DealServicetest(){
        List<Long> mer=dealService.getOptionIdforGivenSlot(1,63175l);
        logger.info(mer.toString());
    }

    @Autowired
    InventoryService inventoryService;

    @Test
    public void InventoryService(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        InventoryServiceModel inventoryServiceModel = inventoryService.getInventoryDetails(30000000129137l,1,dateString,dateString);
        logger.info(inventoryServiceModel.toString());
        if(inventoryServiceModel.getInventory().length>0)
        logger.info(inventoryServiceModel.getInventory()[0].cashback()+"");
    }
    @Autowired
    AppProperties env;

    @Test
    public void EnviromentTest(){
        logger.info(env.getProperty("mechant.service"));
    }
}
