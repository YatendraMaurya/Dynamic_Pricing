package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.mongodb.client.MongoCollection;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.*;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.*;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookingSubscriber implements AppSubscriber<Booking.BookingWrraper>{

    public static final Logger logger=LoggerFactory.getLogger(BookingSubscriber.class);
    public static final int templateid = 260;
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

    @Autowired
    DealService dealService;

    @Autowired
    InventoryService inventoryService;

    public final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public void consume(Booking.BookingWrraper booking) {
        Booking booking1=booking.getPayloadData();
        int slot;
        if(booking.getMsg().equalsIgnoreCase(BookingDao.ACCEPTED)){
            BookingResponse bookingResponse=bookingService.getBookingDetails(booking1.getOrderId());
            if(!WORKFLOW_TYPE.equals(bookingResponse.getOrderDetail().getOrderLines().get(0).getWorkflowType())){
                logger.warn("does not seems to be PAL booking. Ignoring the event {}", AppUtil.toJson(booking));
            }
            else
            {
                Long time=bookingResponse.getBooking().getBookingInitiatedAt();

                long merchantid=bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getMerchants()[0].getMerchantId();
                logger.info(merchantid+"");

                MerchantDetail merchantDetail=merchantService.getMerchant(merchantid);

                Double latitude=merchantDetail.getRs().getAddress().getLatitude();
                Double longitude=merchantDetail.getRs().getAddress().getLongitude();
                Double cashBack = bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getDiscount().getPercent();
                Long accountid=merchantDetail.getRs().getBusinessAccountId();
                slot=AppUtil.StringToIntSlot(bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().
                        getOfferValidity().getValidityTimings()[0].getTags()[0]);
                Long offerid = bookingResponse.getBooking().getOffers()[0].getOfferId();
                Long orderid = bookingResponse.getOrderDetail().getOrderId();

                bookingDao.addbooking(merchantid,"ok",orderid,latitude,longitude,cashBack,time,accountid,slot,offerid);
                logger.info("Adding the booking in the mongo collection: "+AppUtil.toJson(booking));

                Long custid=bookingResponse.getOrderDetail().getCustomerId();
                DiscoveryPostRequest discoveryPostRequest=new DiscoveryPostRequest(custid.toString(),AppConstants.RADIUS,
                            String.valueOf(AppUtil.currentTime()), String.valueOf(AppUtil.currentTime()),latitude,longitude,slot);
                MerchantDiscoveryResponse merchantDiscoveryResponse=discoveryService.getDiscoveryDetail(discoveryPostRequest);
                List<MerchantDiscoveryResponse.Merchant> merchants=merchantDiscoveryResponse.getResult().getMerchant();
                ArrayList<Long> mids=new ArrayList<>();
                for(MerchantDiscoveryResponse.Merchant merchant:merchants) {
                        mids.add(merchant.getMerchantId());
                }

                HashMap<Long,Long> count = bookingDao.getBookingCount(mids);
                Long totalbooking=0l;
                Long maxBooking = -1l;
                for (Long value : count.values()) {
                    logger.info(value+" ");
                    if(value>maxBooking){
                        maxBooking=value;
                    }
                        logger.info("Printed HashMap");
                        totalbooking += value;
                    }
                    for (Long key : count.keySet()) {
                        logger.info(key+" ");
                        logger.info("Printed HashMap");
                    }
                    logger.info(totalbooking+"");
                   // count.put(63175l,1l);
                    for (Long key : count.keySet()) {
                        Long bookingcount = count.get(key);
                        if(bookingcount<(totalbooking-maxBooking)&&bookingcount<(totalbooking*((50*1.0)%100))){
                            //Getting OptionId for given slot
                        List<Long> optionIdforGivenSlot = dealService.getOptionIdforGivenSlot(slot,key);
                        if(optionIdforGivenSlot.size()==0){
                            return;
                        }
                        for(Long optionId : optionIdforGivenSlot){
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = new Date();
                            String date = dateFormat.format(date1);
                            InventoryServiceModel inventoryServiceModel = inventoryService.getInventoryDetails(optionId,1,date,date);
                            Double cashbackFrom = inventoryServiceModel.getInventory()[0].cashback();
                            Long inventoryId = inventoryServiceModel.getInventory()[0].getInventoryId();
                            Long inventorykey = inventoryServiceModel.getItemKey();
                        if(!notificationDao.hasNotifiedRecently(key,optionId)){
                        Long accid = merchantService.getMerchant(key).getRs().getBusinessAccountId();
                        List<Long> users = accountService.getDecisonMaker(accid);
                        if(cashbackFrom<(cashBack*((80*1.0)/100))){
                        Long resp=notificationService.send(key,users,templateid,cashbackFrom,25.0,optionId);
                        if(resp != null){
                            for(Long user: users){
                                notificationDao.addNotification(key,optionId,user,cashbackFrom,30.0,time,templateid,inventoryId,inventorykey);
                            }
                        }}}}}
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
