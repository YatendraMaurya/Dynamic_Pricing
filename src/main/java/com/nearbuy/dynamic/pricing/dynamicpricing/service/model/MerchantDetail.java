package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.io.Serializable;

public class MerchantDetail {
    Rs rs;

    public Rs getRs() {
        return rs;
    }

    public void setRs(Rs rs) {
        this.rs = rs;
    }

    public class Rs{
    //Here BusinessAccountid is accountid
    Addres address;
    Long businessAccountId;

    public Long getBusinessAccountId() {
        return businessAccountId;
    }

    public void setBusinessAccountId(Long businessAccountId) {
        this.businessAccountId = businessAccountId;
    }

    @Override
    public String toString() {
        return "MerchantDetail{" +
                "address=" + address +
                '}';
    }

    public Addres getAddress() {
        return address;
    }

    public void setAddress(Addres address) {
        this.address = address;
    }

    public class Addres {
        @Override
        public String toString() {
            return "Addres{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
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

        Double latitude;
        Double longitude;
    }}
}
