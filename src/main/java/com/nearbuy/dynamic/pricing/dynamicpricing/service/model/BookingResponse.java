package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.util.List;

public class BookingResponse {

    Long customerId;
    Long checkinDate;
    Long checkoutDate;
    Long _id;// only present in kafka Booking event
    String status;
    List<Offer> offers;
    Long orderId;
    Long bookingInitiatedAt;

    public Long getBookingInitiatedAt() {
        return bookingInitiatedAt;
    }

    public void setBookingInitiatedAt(Long bookingInitiatedAt) {
        this.bookingInitiatedAt = bookingInitiatedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    Long bookingId;//returned in the api response of booking service

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Long checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Long getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Long checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "customerId=" + customerId +
                ", checkinDate=" + checkinDate +
                ", checkoutDate=" + checkoutDate +
                ", _id=" + _id +
                ", status='" + status + '\'' +
                ", offers=" + offers +
                ", orderId=" + orderId +
                ", bookingInitiatedAt=" + bookingInitiatedAt +
                ", bookingId=" + bookingId +
                '}';
    }

    public class Offer {
        OfferDetail offerDealDetail;

        public List<SlotDetail> getSlotPrices() {
            return slotPrices;
        }

        public void setSlotPrices(List<SlotDetail> slotPrices) {
            this.slotPrices = slotPrices;
        }

        List<SlotDetail> slotPrices;

        public OfferDetail getOfferDealDetail() {
            return offerDealDetail;
        }

        public void setOfferDealDetail(OfferDetail offerDealDetail) {
            this.offerDealDetail = offerDealDetail;
        }
    }

    public class OfferDetail {
        Long dealId;
        List<Merchant> merchants;
        String workflowType;

        public String getWorkflowType() {
            return workflowType;
        }

        public void setWorkflowType(String workflowType) {
            this.workflowType = workflowType;
        }

        public Long getDealId() {
            return dealId;
        }

        public void setDealId(Long dealId) {
            this.dealId = dealId;
        }

        public List<Merchant> getMerchants() {
            return merchants;
        }

        public void setMerchants(List<Merchant> merchants) {
            this.merchants = merchants;
        }
    }

    public class Merchant {
        Long merchantId;
        String name;
        Location redemptionLocation;

        public Long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Long merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Location getRedemptionLocation() {
            return redemptionLocation;
        }

        public void setRedemptionLocation(Location redemptionLocation) {
            this.redemptionLocation = redemptionLocation;
        }
    }

    public class Location {
        Double latitude;
        Double longitude;
        String cityTown;

        public String getCityTown() {
            return cityTown;
        }

        public void setCityTown(String cityTown) {
            this.cityTown = cityTown;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

    public class SlotDetail {
        String date;
        Long timeSlot;
        Discount discount;

        public Discount getDiscount() {
            return discount;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        public String getDate() {
            return date;
        }


        public void setDate(String date) {
            this.date = date;
        }

        public Long getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(Long timeSlot) {
            this.timeSlot = timeSlot;
        }
    }

    public class Discount {
        Long percent;
        Long maxCap;
        Long minOrderValue;

        public Long getPercent() {
            return percent;
        }

        public void setPercent(Long percent) {
            this.percent = percent;
        }

        public Long getMaxCap() {
            return maxCap;
        }

        public void setMaxCap(Long maxCap) {
            this.maxCap = maxCap;
        }

        public Long getMinOrderValue() {
            return minOrderValue;
        }

        public void setMinOrderValue(Long minOrderValue) {
            this.minOrderValue = minOrderValue;
        }
    }
}
