package com.nearbuy.dynamic.pricing.dynamicpricing.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.nearbuy.dynamic.pricing.dynamicpricing.Config.MongoConfig;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.PalBooking;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.mongodb.client.model.Filters.eq;


@Component
public class BookingDao {

    public static final Logger logger=LoggerFactory.getLogger(BookingDao.class);

    @Autowired
    @Qualifier(value = "Dynamic_Collection")
    MongoCollection BookingCollection;

    public static final String MERCHANT_ID = "merchantId";
    public static final String STATUS = "status";
    public static final String CASH_BACK = "cashback";
    public static final String ORDER_ID = "orderId";
    public static final String TIMESLOT = "timeSlot";
    public static final String ACCEPTED = "SUCCESSFUL_PURCHASE";
    public static final String CANCELLED = "CANCELLED";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ACCOUNT_ID = "businessAccId";
    public static final String SLOT = "slotId";
    public static final String OFFER_ID = "offerId";

    public void addbooking(long merchantid, String status, long orderid, Double latitude, Double longitude, Double cashcback,
                           long time,Long accountid,int slot,Long offerid) {
        Document doc=new Document(MERCHANT_ID,merchantid).append(STATUS,status).append(ORDER_ID,orderid).append(CASH_BACK,cashcback).
                append(TIMESLOT,time).append(LATITUDE,latitude).append(LONGITUDE,longitude).
                append(ACCOUNT_ID,accountid).append(SLOT,slot).append(OFFER_ID,offerid);
        BookingCollection.insertOne(doc);
        logger.info("Added to MongoDatabase");
    }
    public void cancelBooking(long orderid) {
        BookingCollection.updateOne(Filters.eq(ORDER_ID,orderid), Updates.set(STATUS,CANCELLED));
    }
    public boolean ispalBooking(long orderid){
        return BookingCollection.find(Filters.eq(ORDER_ID,orderid)).first()!=null;
    }

    public String getBookingStatus(long orderid){
        return ((PalBooking) BookingCollection.withDocumentClass(PalBooking.class).find(Filters.eq(ORDER_ID,orderid)).first()).getStatus();
    }

    private Collection<Booking> bookingCollection;

    public void getBookingbyOrderId(String objectid)
    {
        Object palBooking= BookingCollection.find(eq("_id", new ObjectId(objectid)),PalBooking.class).first();
        logger.info(palBooking.toString());
    }

    public HashMap<Long, Long> getBookingCount(ArrayList<Long> merchantIds){
        logger.info(merchantIds.toString());
        HashMap<Long, Long> retval = new HashMap<>();
        for(Long merchantId : merchantIds){
            if(BookingCollection.find(Filters.and(Filters.eq(MERCHANT_ID, merchantId) ,
                    Filters.gte(TIMESLOT, AppUtil.currentTime() - AppConstants.MINUTE))).first() != null){
                if(!retval.containsKey(merchantId)){
                    logger.info("IN BOOKING COUNT IF");
                    logger.info(merchantId+"");
                    retval.put(merchantId, 1l);
                } else {
                    logger.info("IN BOOKING COUNT");
                    logger.info(merchantId+"");
                    retval.put(merchantId, retval.get(merchantId)+1);
                }
            }
        }
        return retval;
    }

    public Boolean update(Long merchantid, Double updtaedCb) {
        BookingCollection.updateOne(Filters.eq(MERCHANT_ID,merchantid), Updates.set(CASH_BACK,updtaedCb));
        return true;
    }
}
