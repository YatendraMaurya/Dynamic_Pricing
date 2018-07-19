package com.nearbuy.dynamic.pricing.model;

import java.util.List;

public class Notification {
    Long inventoryId;
    int inventoryTypeId;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getInventoryTypeId() {
        return inventoryTypeId;
    }

    public void setInventoryTypeId(int inventoryTypeId) {
        this.inventoryTypeId = inventoryTypeId;
    }

    public String getDiscountDetail() {
        return discountDetail;
    }

    public void setDiscountDetail(String discountDetail) {
        this.discountDetail = discountDetail;
    }

    String discountDetail;

    public class NotificationUtill{
        List<Notification> inventories;

        public List<Notification> getInventories() {
            return inventories;
        }

        public void setInventories(List<Notification> inventories) {
            this.inventories = inventories;
        }
    }

    public class NotifiactionWrapper{
        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public NotificationUtill getPayloadData() {
            return payloadData;
        }

        public void setPayloadData(NotificationUtill payloadData) {
            this.payloadData = payloadData;
        }

        String msg;
        NotificationUtill payloadData;

    }
}
