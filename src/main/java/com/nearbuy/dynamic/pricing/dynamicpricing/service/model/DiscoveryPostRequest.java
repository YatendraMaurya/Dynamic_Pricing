package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

public class DiscoveryPostRequest {
    private Long CUSTOMER_ID;
    private Long RADIUS;
    private Long START_DATE;
    private Long END_DATE;

    public int getSLOT_ID() {
        return SLOT_ID;
    }

    public void setSLOT_ID(int SLOT_ID) {
        this.SLOT_ID = SLOT_ID;
    }

    private int SLOT_ID;
    private Double LAT, LNG;

    public DiscoveryPostRequest(String CUSTOMER_ID, Long RADIUS, String START_DATE, String END_DATE, Double LAT, Double LNG,int slotid) {
        this.CUSTOMER_ID = Long.valueOf(CUSTOMER_ID);
        this.RADIUS = RADIUS;
        this.START_DATE = Long.valueOf(START_DATE);
        this.END_DATE = Long.valueOf(END_DATE);
        this.LAT = LAT;
        this.LNG = LNG;
        this.SLOT_ID=slotid;
    }

    public Long getCUSTOMER_ID() {

        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = Long.valueOf(CUSTOMER_ID);
    }

    public Long getRADIUS() {
        return RADIUS;
    }

    public void setRADIUS(Long RADIUS) {
        this.RADIUS = RADIUS;
    }

    public Long getSTART_DATE() {
        return START_DATE;
    }

    public void setSTART_DATE(String START_DATE) {
        this.START_DATE = Long.valueOf(START_DATE);
    }

    public Long getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = Long.valueOf(END_DATE);
    }

    public Double getLAT() {
        return LAT;
    }

    public void setLAT(Double LAT) {
        this.LAT = LAT;
    }

    public Double getLNG() {
        return LNG;
    }

    public void setLNG(Double LNG) {
        this.LNG = LNG;
    }
}
