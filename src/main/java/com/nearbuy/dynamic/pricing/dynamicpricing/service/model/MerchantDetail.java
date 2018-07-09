package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.io.Serializable;

public class MerchantDetail {
    Long merchantId;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

}
