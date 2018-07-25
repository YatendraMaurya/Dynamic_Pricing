package com.nearbuy.dynamic.pricing.dynamicpricing.dao.model;

import java.util.ArrayList;
import java.util.List;

public class PalBooking {
    private long orderid;
    private long merchantid;
    private String status;
    private float latitude;
    private float longitude;
    private long time;
    private Long businessAccId;
    private int slot;
    private Long offerid;
    private Double cashback;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }

    public Long getOfferid() {
        return offerid;
    }

    public void setOfferid(Long offerid) {
        this.offerid = offerid;
    }

    public int getslot() {
        return slot;
    }

    public void setslot(int slotid) {
        slot = slotid;
    }

    public Long getBusinessAccId() {
        return businessAccId;
    }

    public void setBusinessAccId(Long businessAccId) {
        this.businessAccId = businessAccId;
    }


    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }

    public long getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(long merchantid) {
        this.merchantid = merchantid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }



    @Override
    public String toString() {
        return "PalBooking{" +
                "orderid=" + orderid +
                ", merchantid=" + merchantid +
                ", status='" + status + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", time=" + time +
                ", businessAccId=" + businessAccId +
                ", cashback=" + cashback +
                '}';
    }
}
