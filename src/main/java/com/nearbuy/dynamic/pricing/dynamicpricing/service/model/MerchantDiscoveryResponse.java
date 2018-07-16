package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.util.List;

public class MerchantDiscoveryResponse {
    Result result;

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "MerchantDiscoveryResponse{" +
                "result=" + result +
                '}';
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        public List<Merchant> getMerchant() {
            return merchants;
        }

        public void setMerchant(List<Merchant> merchant) {
            this.merchants = merchant;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "merchant=" + merchants +
                    '}';
        }

        List<Merchant> merchants;
    }

    private class Merchant {
        @Override
        public String toString() {
            return "Merchant{" +
                    "merchantId=" + merchantId +
                    '}';
        }

        public long getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(long merchantId) {
            this.merchantId = merchantId;
        }

        long merchantId;
    }
}
