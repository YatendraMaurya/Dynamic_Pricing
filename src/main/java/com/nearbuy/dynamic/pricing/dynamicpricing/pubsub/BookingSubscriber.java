package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.mongodb.client.MongoCollection;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.*;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.DiscoveryPostRequest;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDetail;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDiscoveryResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class BookingSubscriber implements AppSubscriber<Booking.BookingWrraper>{

    public static final Logger logger=LoggerFactory.getLogger(BookingSubscriber.class);
    public static final int tempid = 260;
    public final static String WORKFLOW_TYPE = "BOOKING_TYPE2";
    @Autowired
    BookingService bookingService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    BookingDao bookingDao;

    @Autowired
    NotificationService notificationService;

    @Autowired
    DiscoveryService discoveryService;

    @Autowired
    AccountService accountService;

    @Autowired
    NotificationDao notificationDao;

    public final String DATE_FORMAT = "yyyy-MM-dd";

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
                long time=bookingResponse.getBooking().getBookingInitiatedAt();
                logger.info("Adding the booking in the mongo collection: "+AppUtil.toJson(booking));
                long merchantid=bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getMerchants()[0].getMerchantId();
                logger.info(merchantid+"");
                MerchantDetail merchantDetail=merchantService.getMerchant(merchantid);
                Double latitude=merchantDetail.getAddress().getLatitude();
                Double longitude=merchantDetail.getAddress().getLongitude();
                ArrayList<Double> cashbacks = new ArrayList<>();
                for(BookingResponse.OrderLine orderLine : bookingResponse.getOrderDetail().getOrderLines()){
                    for(BookingResponse.OrderBomBOs orderBomBOs : orderLine.getProductBO().getOrderBomBOs()){
                        cashbacks.add(orderBomBOs.getCashback());
                    }
                }
                long currtime= 0;
                try {
                    currtime = AppUtil.getTimeFromDate(DATE_FORMAT,bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getDate())
                            +bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getTimeSlot();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long accountid=merchantDetail.getBusinessAccountId();
                bookingDao.addbooking(merchantid,"ok",bookingResponse.getOrderDetail().getOrderId(),latitude,longitude,cashbacks,time,accountid);
                if (true) {
                    Long custid=bookingResponse.getOrderDetail().getCustomerId();
                    DiscoveryPostRequest discoveryPostRequest=new DiscoveryPostRequest(custid.toString(),AppConstants.RADIUS,
                            String.valueOf(AppUtil.currentTime()), String.valueOf(AppUtil.currentTime()),latitude,longitude);
                    MerchantDiscoveryResponse merchantDiscoveryResponse=discoveryService.getDiscoveryDetail(discoveryPostRequest);
                    List<MerchantDiscoveryResponse.Merchant> merchants=merchantDiscoveryResponse.getResult().getMerchant();
                    ArrayList<Long> mids=new ArrayList<>();
                    for(MerchantDiscoveryResponse.Merchant merchant:merchants) {
                        mids.add(merchant.getMerchantId());
                    }
                    HashMap<Long,Long> count = bookingDao.getBookingCount(mids);
                    Long totalbooking=0l;
                    for (Long value : count.values()) {
                        logger.info(value+" ");
                        logger.info("Printed HashMap");
                        totalbooking += value;
                    }
                    for (Long key : count.keySet()) {
                        logger.info(key+" ");
                        logger.info("Printed HashMap");
                    }
                    logger.info(totalbooking+"");
                    count.put(63175l,1l);
                    for (Long key : count.keySet()) {
                        Long accid = merchantService.getMerchant(key).getBusinessAccountId();
                        List<Long> users = accountService.getDecisonMaker(accid);
                        Long resp=notificationService.send(key,users,tempid,20.0,25.0);
                        if(resp != null){
                            for(Long user: users){
                                notificationDao.addNotification(key,user,20.0,30.0,time,tempid);
                            }
                        }
                    }
                } else {
                    logger.info("PAL reservation UNDER 90mins of booking so not sending any notifications");
                }
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
