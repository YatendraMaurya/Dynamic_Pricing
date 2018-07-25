package com.nearbuy.dynamic.pricing.model;

public class Booking {
    Long orderId;
    Long customerId;
    long eventOccuredAt;
    long orderInitiatedAt;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public long getEventOccuredAt() {
        return eventOccuredAt;
    }

    public void setEventOccuredAt(long eventOccuredAt) {
        this.eventOccuredAt = eventOccuredAt;
    }

    public long getOrderInitiatedAt() {
        return orderInitiatedAt;
    }

    public void setOrderInitiatedAt(long orderInitatedAt) {
        this.orderInitiatedAt = orderInitatedAt;
    }

    public class BookingWrraper {
        Booking payloadData;
        String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Booking getPayloadData() {
            return payloadData;
        }

        public void setPayloadData(Booking payloadData) {
            this.payloadData = payloadData;
        }
    }
}
