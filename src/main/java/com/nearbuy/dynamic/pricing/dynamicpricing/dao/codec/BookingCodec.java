package com.nearbuy.dynamic.pricing.dynamicpricing.dao.codec;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.PalBooking;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.bson.Document;
import org.omg.IOP.Codec;

import java.util.ArrayList;

public class BookingCodec extends AppCodec<PalBooking> {

    @Override
    protected PalBooking _decode(Document doc) {
        PalBooking booking=new PalBooking();
        booking.setOrderid(doc.getLong(BookingDao.ORDER_ID));
        booking.setLatitude(Float.valueOf(doc.get(BookingDao.LATITUDE)+""));
        booking.setLongitude(Float.valueOf(doc.get(BookingDao.LONGITUDE)+""));
        booking.setCahsback((ArrayList<Double>)doc.get(BookingDao.CASH_BACK));
        booking.setStatus(doc.getString(BookingDao.STATUS));
        booking.setTime(doc.getLong(BookingDao.TIMESLOT));
        booking.setMerchantid(doc.getLong(BookingDao.MERCHANT_ID));
        booking.setBusinessAccId(doc.getLong(BookingDao.ACCOUNT_ID));
        return booking;
    }

    @Override
    protected Document _encode(PalBooking booking) {
        Document document=new Document();
        document.put(BookingDao.MERCHANT_ID,booking.getMerchantid());
        document.put(BookingDao.ORDER_ID,booking.getOrderid());
        document.put(BookingDao.TIMESLOT,booking.getTime());
        document.put(BookingDao.STATUS,booking.getStatus());
        document.put(BookingDao.LONGITUDE,booking.getLongitude());
        document.put(BookingDao.LATITUDE,booking.getLatitude());
        document.put(BookingDao.CASH_BACK,booking.getCahsback());
        document.put(BookingDao.ACCOUNT_ID,booking.getBusinessAccId());
        return  document;
    }
}
