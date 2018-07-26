package com.nearbuy.dynamic.pricing.dynamicpricing.pubsub;

import com.mongodb.client.MongoCollection;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.BookingDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.dao.NotificationDao;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.*;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.*;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppConstants;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import com.nearbuy.dynamic.pricing.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    AppProperties env;



    @Override
    public void consume(Booking.BookingWrraper booking) {

         int thresholddif = Integer.valueOf(env.getProperty("threshold.differance"));
         Double thresholdper = Double.valueOf(env.getProperty("threshold.percentage"));
         int thresholdCbPer = Integer.valueOf(env.getProperty("threshold.cb.per"));

        Booking booking1 = booking.getPayloadData();
        int slot;
        if (booking1.getStatus().equalsIgnoreCase(BookingDao.ACCEPTED)) {
            logger.info("getting booking details from orderId = " + booking1.getOrderId());
            BookingResponse bookingResponse = bookingService.getBookingDetails(booking1.getOrderId());
            if (!WORKFLOW_TYPE.equals(bookingResponse.getOrderDetail().getOrderLines().get(0).getWorkflowType())) {
                logger.warn("does not seems to be PAL booking. Ignoring the event {}", AppUtil.toJson(booking));
            } else {
                logger.info("got a palbooking");
                Long time = bookingResponse.getBooking().getBookingInitiatedAt();

                long merchantid = bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getMerchants()[0].getMerchantId();
                logger.info("extracting merchantId from Booking " + merchantid + "");

                MerchantDetail merchantDetail = merchantService.getMerchant(merchantid);
                logger.info("Getting Merchant Detail of MerchantId" + merchantid);

                Double latitude = merchantDetail.getRs().getAddress().getLatitude();
                Double longitude = merchantDetail.getRs().getAddress().getLongitude();
                Double cashBack = bookingResponse.getBooking().getOffers()[0].getSlotPrices()[0].getDiscount().getPercent();
                Long accountid = merchantDetail.getRs().getBusinessAccountId();
                slot = AppUtil.StringToIntSlot(bookingResponse.getBooking().getOffers()[0].getOfferDealDetail().getOfferValidity().getValidityTimings()[0].getTags()[0]);
                Long offerid = bookingResponse.getBooking().getOffers()[0].getOfferId();
                Long orderid = bookingResponse.getOrderDetail().getOrderId();
                logger.info("Slot for booking is" + slot);

                bookingDao.addbooking(merchantid, "ok", orderid, latitude, longitude, cashBack, time, accountid, slot, offerid);
                logger.info("Adding the booking in the mongo collection: " + AppUtil.toJson(booking));

                Long custid = bookingResponse.getOrderDetail().getCustomerId();

                DiscoveryPostRequest discoveryPostRequest = new DiscoveryPostRequest(custid.toString(), AppConstants.RADIUS,
                        String.valueOf(AppUtil.currentTime()), String.valueOf(AppUtil.currentTime()), latitude, longitude, slot);
                logger.info("getting the nearbuy merchant withing X'km range");
                MerchantDiscoveryResponse merchantDiscoveryResponse = discoveryService.getDiscoveryDetail(discoveryPostRequest);
                List < MerchantDiscoveryResponse.Merchant > merchants = merchantDiscoveryResponse.getResult().getMerchant();
                ArrayList < Long > mids = new ArrayList < > ();
                for (MerchantDiscoveryResponse.Merchant merchant: merchants) {
                    mids.add(merchant.getMerchantId());
                }
                logger.info("Calculating total booking count within X'km range");
                HashMap < Long, Long > count = bookingDao.getBookingCount(mids);
                Long totalbooking = 0l;
                Long maxBooking = -1l;
                Long maxBookingId=0l;
                for (HashMap.Entry < Long, Long > entry: count.entrySet()) {
                    Long value = entry.getValue();
                    Long key = entry.getKey();
                    if (value > maxBooking) {
                        maxBooking = value;
                        maxBookingId = key;
                    }
                    totalbooking += value;
                }
                logger.info(totalbooking + "");

                Double goodCashback = cashBack;
                List < Long > optionIdforGivenSlot1 = dealService.getOptionIdforGivenSlot(slot, maxBookingId);
                for(Long optionId1 : optionIdforGivenSlot1){
                    InventoryServiceModel inventoryServiceModel = inventoryService.getInventoryDetails(optionId1, 1, AppUtil.todayDate(), AppUtil.todayDate());
                    if(inventoryServiceModel != null)
                        goodCashback = inventoryServiceModel.getInventory()[0].cashback();
                }

                    for (Long key: count.keySet()) {
                        Long bookingcount = count.get(key);
                        if ((maxBooking - bookingcount) > thresholddif && ((maxBooking * 1.0) / (bookingcount * 1.0)) > thresholdper) {
                            logger.info("Getting OptionId for given slot and merchantId");
                            List < Long > optionIdforGivenSlot = dealService.getOptionIdforGivenSlot(slot, key);
                            if (optionIdforGivenSlot.size() == 0) {
                                return;
                            }
                            Double suggestedCb = goodCashback * ((thresholdCbPer * 1.0) / 100);
                            for (Long optionId: optionIdforGivenSlot) {
                                logger.info("getting Inventory Details of optionId" + optionId);
                                InventoryServiceModel inventoryServiceModel = inventoryService.getInventoryDetails(optionId, 1, AppUtil.todayDate(), AppUtil.todayDate());
                                if(inventoryServiceModel.getInventory().length!=0){
                                    Double cashbackFrom = inventoryServiceModel.getInventory()[0].cashback();
                                    Long inventoryId = inventoryServiceModel.getInventory()[0].getInventoryId();
                                    Long inventorykey = inventoryServiceModel.getItemKey();
                                    if (!notificationDao.hasNotifiedRecently(key, optionId)) {
                                        Long accid = merchantService.getMerchant(key).getRs().getBusinessAccountId();
                                        List < Long > users = accountService.getDecisonMaker(accid);
                                        if (cashbackFrom < suggestedCb) {
                                            Long resp = notificationService.send(key, users, templateid, cashbackFrom, suggestedCb, optionId);
                                            if (resp != null) {
                                                for (Long user: users) {
                                                    logger.info("adding sent notification to Mongo");
                                                    notificationDao.addNotification(key, optionId, user, cashbackFrom, suggestedCb, time, templateid, inventoryId, inventorykey);
                                            }
                                        }
                                    }
                                }
                            }}
                        }
                    }
            }
        } else if (bookingDao.CANCELLED.equalsIgnoreCase(booking1.getStatus())) {
            //delete the booking trigger if any and update the collection to
            bookingDao.cancelBooking(booking1.getOrderId());
            logger.info("updated the palBooking for " + AppUtil.toJson(booking1));
        } else {
            logger.info("ignoring pal Reservation for the rest of the statuses : " + AppUtil.toJson(booking1));
        }
    }

    @Override
    public Class getClazz() {
        return Booking.BookingWrraper.class;
    }
}
