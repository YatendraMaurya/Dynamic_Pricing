package com.nearbuy.dynamic.pricing.model;

public class Booking {
    Long bookingId;
    String status;
    Long orderId;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public class BookingWrraper {
        Booking payloadData;

        public Booking getPayloadData() {
            return payloadData;
        }

        public void setPayloadData(Booking payloadData) {
            this.payloadData = payloadData;
        }
    }
}
