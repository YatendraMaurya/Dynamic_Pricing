package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.io.Serializable;

public class MerchantDetail {
    Addres address;

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

        float latitude;
        float longitude;
    }
}
