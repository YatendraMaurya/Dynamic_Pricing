package com.nearbuy.dynamic.pricing.dynamicpricing.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.nearbuy.dynamic.pricing.dynamicpricing.Config.MongoConfig;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.PalBooking;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
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

    public void addbooking(long merchantid, String status, long orderid, float latitude, float longitude, ArrayList<Double> cashcback, long time) {
        Document doc=new Document(MERCHANT_ID,merchantid).append(STATUS,status).append(ORDER_ID,orderid).append(CASH_BACK,cashcback).
                append(TIMESLOT,time).append(LATITUDE,latitude).append(LONGITUDE,longitude);
        BookingCollection.insertOne(doc);
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




}
