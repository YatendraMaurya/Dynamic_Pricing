package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.util.Arrays;

public class DealServiceResponse {
    int statusCode;
    Result[] result;
    // Here key is optionId

    @Override
    public String toString() {
        return "DealServiceResponse{" +
                "statusCode=" + statusCode +
                ", result=" + Arrays.toString(result) +
                '}';
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Result[] getResult() {
        return result;
    }

    public void setResult(Result[] result) {
        this.result = result;
    }

    public class Result {
        @Override
        public String toString() {
            return "Result{" +
                    "offers=" + Arrays.toString(offers) +
                    '}';
        }

        public Offers[] getOffers() {
            return offers;
        }

        public void setOffers(Offers[] offers) {
            this.offers = offers;
        }

        Offers[] offers;
    }

    public class Offers {
        @Override
        public String toString() {
            return "Offers{" +
                    "key=" + key +
                    ", offerValidity=" + offerValidity +
                    '}';
        }

        public OfferValidity getOfferValidity() {
            return offerValidity;
        }

        public void setOfferValidity(OfferValidity offerValidity) {
            this.offerValidity = offerValidity;
        }

        public Long getKey() {

            return key;
        }

        public void setKey(Long key) {
            this.key = key;
        }

        Long key;
        OfferValidity offerValidity;
    }

    public class OfferValidity{
        @Override
        public String toString() {
            return "OfferValidity{" +
                    "validityTimings=" + Arrays.toString(validityTimings) +
                    '}';
        }

        public ValidityTiming[] getValidityTimings() {
            return validityTimings;
        }

        public void setValidityTimings(ValidityTiming[] validityTimings) {
            this.validityTimings = validityTimings;
        }

        ValidityTiming[] validityTimings;
    }

    public class ValidityTiming{
        String[] tags;

        @Override
        public String toString() {
            return "ValidityTiming{" +
                    "tags=" + Arrays.toString(tags) +
                    '}';
        }

        public String[] getTags() {
            return tags;
        }

        public void setTags(String[] tags) {
            this.tags = tags;
        }
    }

}
