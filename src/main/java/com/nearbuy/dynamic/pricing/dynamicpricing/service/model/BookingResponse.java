package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

public class BookingResponse {
    //Here OfferId is OptionId

    private OrderDetail orderDetail;
    private Booking booking;

    @Override
    public String toString() {
        return "BookingResponse{" +
                "orderDetail=" + orderDetail +
                ", booking=" + booking +
                '}';
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public class Booking{
        Offer[] offers;

        @Override
        public String toString() {
            return "Booking{" +
                    "offers=" + Arrays.toString(offers) +
                    ", bookingInitiatedAt=" + bookingInitiatedAt +
                    '}';
        }

        long bookingInitiatedAt;

        public long getBookingInitiatedAt() {
            return bookingInitiatedAt;
        }

        public void setBookingInitiatedAt(long bookingInitiatedAt) {
            this.bookingInitiatedAt = bookingInitiatedAt;
        }

        public Offer[] getOffers() {
            return offers;
        }

        public void setOffers(Offer[] offers) {
            this.offers = offers;
        }
    }
    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public class OrderDetail{
        long orderId;
        long customerId;

        public long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
        }

        @Override
        public String toString() {
            return "OrderDetail{" +
                    "orderId=" + orderId +
                    ", orderLine=" + orderLines +
                    '}';
        }

        List<OrderLine> orderLines;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public List<OrderLine> getOrderLines() {
            return orderLines;
        }

        public void setOrderLines(List<OrderLine> orderLines) {
            this.orderLines = orderLines;
        }
    }

    public class OrderLine {
        ProductBo productBO;

        public String getWorkflowType() {
            return workflowType;
        }

        public void setWorkflowType(String workflowType) {
            this.workflowType = workflowType;
        }

        String workflowType;

        public ProductBo getProductBO() {
            return productBO;
        }

        @Override
        public String toString() {
            return "OrderLine{" +
                    "productBO=" + productBO +
                    ", workflowType='" + workflowType + '\'' +
                    '}';
        }

        public void setProductBO(ProductBo productBO) {
            this.productBO = productBO;
        }
    }

    public class ProductBo {
        @Override
        public String toString() {
            return "ProductBo{" +
                    "productId=" + productId +
                    ", orderBomBOs=" + orderBomBOs +
                    '}';
        }

        long productId;
        List<OrderBomBOs> orderBomBOs;

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public List<OrderBomBOs> getOrderBomBOs() {
            return orderBomBOs;
        }

        public void setOrderBomBOs(List<OrderBomBOs> orderBomBOs) {
            this.orderBomBOs = orderBomBOs;
        }
    }

    public class OrderBomBOs {
        String discount;

        @Override
        public String toString() {
            return "OrderBomBOs{" +
                    "discount='" + discount + '\'' +
                    '}';
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public double getCashback(){
            return Double.valueOf(discount.split(",")[1].split((":"))[1]);
        }
    }

    public class Offer {
        @Override
        public String toString() {
            return "Offer{" +
                    "slotPrices=" + Arrays.toString(slotPrices) +
                    ", offerDealDetail=" + offerDealDetail +
                    '}';
        }

        SlotPrice[] slotPrices;
        Long offerId;

        public Long getOfferId() {
            return offerId;
        }

        public void setOfferId(Long offerId) {
            this.offerId = offerId;
        }

        public SlotPrice[] getSlotPrices() {
            return slotPrices;
        }

        public void setSlotPrices(SlotPrice[] slotPrices) {
            this.slotPrices = slotPrices;
        }

        OfferDealDetail offerDealDetail;

        public OfferDealDetail getOfferDealDetail() {
            return offerDealDetail;
        }

        public void setOfferDealDetail(OfferDealDetail offerDealDetail) {
            this.offerDealDetail = offerDealDetail;
        }
    }

    public class OfferDealDetail {
        Merchant[] merchants;

        public OfferValidity getOfferValidity() {
            return offerValidity;
        }

        public void setOfferValidity(OfferValidity offerValidity) {
            this.offerValidity = offerValidity;
        }

        OfferValidity offerValidity;


        @Override
        public String toString() {
            return "OfferDealDetail{" +
                    "merchants=" + Arrays.toString(merchants) +
                    '}';
        }

        public Merchant[] getMerchants() {
            return merchants;
        }

        public void setMerchants(Merchant[] merchants) {
            this.merchants = merchants;
        }
    }

    public class Merchant {
        @Override
        public String toString() {
            return "Merchant{" +
                    "merchantId=" + merchantId +
                    '}';
        }

        private Long merchantId;

        public Long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Long merchantId) {
            this.merchantId = merchantId;
        }
    }

    public class SlotPrice {
        long timeSlot;
        String date;
        Discount discount;

        public Discount getDiscount() {
            return discount;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        @Override
        public String toString() {
            return "SlotPrice{" +
                    "timeSlot=" + timeSlot +
                    ", date='" + date + '\'' +
                    '}';
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(long timeSlot) {
            this.timeSlot = timeSlot;
        }
    }

    public class OfferValidity {
        public ValidityTiming[] getValidityTimings() {
            return validityTimings;
        }

        public void setValidityTimings(ValidityTiming[] validityTimings) {
            this.validityTimings = validityTimings;
        }

        ValidityTiming[] validityTimings;
    }

    public class ValidityTiming {
        String[] tags;

        public String[] getTags() {
            return tags;
        }

        public void setTags(String[] tags) {
            this.tags = tags;
        }
    }

    public class Discount {
        Double percent;

        public Double getPercent() {
            return percent;
        }

        public void setPercent(Double percent) {
            this.percent = percent;
        }
    }
}
