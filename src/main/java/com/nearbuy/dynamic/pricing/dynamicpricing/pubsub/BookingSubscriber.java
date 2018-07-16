package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.BookingService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.MerchantService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDetail;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookingSubscriber implements AppSubscriber<Booking.BookingWrraper>{

    public static final Logger logger=LoggerFactory.getLogger(BookingSubscriber.class);
    public final static String WORKFLOW_TYPE = "BOOKING_TYPE2";
    @Autowired
    BookingService bookingService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    BookingDao bookingDao;


    @Override
    public void consume(Booking.BookingWrraper booking) {
        Booking booking1=booking.getPayloadData();
        if(booking.getMsg().equalsIgnoreCase(BookingDao.ACCEPTED)){
            BookingResponse bookingResponse=bookingService.getBookingDetails(booking1.getOrderId());
            if(!WORKFLOW_TYPE.equals(bookingResponse.getOrderDetail().getOrderLines().get(0).getWorkflowType())){
                logger.warn("does not seems to be PAL booking. Ignoring the event {}", AppUtil.toJson(booking));
            }
            else
            {
                long time=booking1.getOrderInitiatedAt();
                logger.info("Adding the booking in the mongo collection: "+AppUtil.toJson(booking));
                long merchantid=bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getMerchants()[0].getMerchantId();
                MerchantDetail merchantDetail=merchantService.getMerchant(merchantid);
                float latitude=merchantDetail.getAddress().getLatitude();
                float longitude=merchantDetail.getAddress().getLongitude();
                ArrayList<Double> cashbacks = new ArrayList<>();
                for(BookingResponse.OrderLine orderLine : bookingResponse.getOrderDetail().getOrderLines()){
                    for(BookingResponse.OrderBomBOs orderBomBOs : orderLine.getProductBO().getOrderBomBOs()){
                        cashbacks.add(orderBomBOs.getCashback());
                    }
                }
                bookingDao.addbooking(merchantid,"ok",bookingResponse.getOrderDetail().getOrderId(),latitude,longitude,cashbacks,time);
            }
        }
        else if (bookingDao.CANCELLED.equalsIgnoreCase(booking.getMsg())) {
            //delete the booking trigger if any and update the collection to
            bookingDao.cancelBooking(booking1.getOrderId());
            logger.info("updated the palBooking for "+AppUtil.toJson(booking1));
        } else {
            logger.info("ignoring pal Reservation for the rest of the statuses : "+AppUtil.toJson(booking1));
        }
        return;
    }

    @Override
    public Class getClazz() {
        return Booking.BookingWrraper.class;
    }
}
