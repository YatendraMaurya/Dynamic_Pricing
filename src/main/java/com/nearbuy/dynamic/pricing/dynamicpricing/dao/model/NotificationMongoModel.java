package com.nearbuy.dynamic.pricing.dynamicpricing.dao.model;

public class NotificationMongoModel {
    public  Long USER_ID;
    public  Double CASH_BACK_TO;
    public  Double CASH_BACK_FROM ;
    public  Long TIMESLOT ;
    public  int TEMPLATE_ID;
    public Long merchantid;
    public Long optionid;
    public Long INVENTORY_ID;
    public Long INVENTORY_KEY;

    public Long getINVENTORY_ID() {
        return INVENTORY_ID;
    }

    public void setINVENTORY_ID(Long INVENTORY_ID) {
        this.INVENTORY_ID = INVENTORY_ID;
    }

    public Long getINVENTORY_KEY() {
        return INVENTORY_KEY;
    }

    public void setINVENTORY_KEY(Long INVENTORY_KEY) {
        this.INVENTORY_KEY = INVENTORY_KEY;
    }

    public Long getOptionid() {
        return optionid;
    }

    public void setOptionid(Long optionid) {
        this.optionid = optionid;
    }

    @Override
    public String toString() {
        return "NotificationMongoModel{" +
                "USER_ID=" + USER_ID +
                ", CASH_BACK_TO=" + CASH_BACK_TO +
                ", CASH_BACK_FROM=" + CASH_BACK_FROM +
                ", TIMESLOT=" + TIMESLOT +
                ", TEMPLATE_ID=" + TEMPLATE_ID +
                ", merchantid=" + merchantid +
                '}';
    }

    public Long getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Long merchantid) {
        this.merchantid = merchantid;
    }

    public Long getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(Long USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Double getCASH_BACK_TO() {
        return CASH_BACK_TO;
    }

    public void setCASH_BACK_TO(Double CASH_BACK_TO) {
        this.CASH_BACK_TO = CASH_BACK_TO;
    }

    public Double getCASH_BACK_FROM() {
        return CASH_BACK_FROM;
    }

    public void setCASH_BACK_FROM(Double CASH_BACK_FROM) {
        this.CASH_BACK_FROM = CASH_BACK_FROM;
    }

    public Long getTIMESLOT() {
        return TIMESLOT;
    }

    public void setTIMESLOT(Long TIMESLOT) {
        this.TIMESLOT = TIMESLOT;
    }

    public int getTEMPLATE_ID() {
        return TEMPLATE_ID;
    }

    public void setTEMPLATE_ID(int TEMPLATE_ID) {
        this.TEMPLATE_ID = TEMPLATE_ID;
    }
}
