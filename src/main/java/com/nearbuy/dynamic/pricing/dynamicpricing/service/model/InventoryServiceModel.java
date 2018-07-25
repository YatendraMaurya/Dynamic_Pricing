package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.util.Arrays;

public class InventoryServiceModel {
    Long itemKey;
    Inventory[] inventory;

    @Override
    public String toString() {
        return "InventoryServiceModel{" +
                "itemKey=" + itemKey +
                ", inventory=" + Arrays.toString(inventory) +
                '}';
    }

    public Long getItemKey() {
        return itemKey;
    }

    public void setItemKey(Long itemKey) {
        this.itemKey = itemKey;
    }

    public Inventory[] getInventory() {
        return inventory;
    }

    public void setInventory(Inventory[] inventory) {
        this.inventory = inventory;
    }

    public class Inventory {
        Long inventoryId;

        public Long getInventoryId() {
            return inventoryId;
        }

        public void setInventoryId(Long inventoryId) {
            this.inventoryId = inventoryId;
        }

        @Override
        public String toString() {
            return "Inventory{" +
                    "inventoryId=" + inventoryId +
                    ", discountDetail='" + discountDetail + '\'' +
                    '}';
        }

        public String getDiscountDetail() {
            return discountDetail;
        }

        public void setDiscountDetail(String discountDetail) {
            this.discountDetail = discountDetail;
        }

        public Double cashback(){
            return Double.valueOf(discountDetail.split(",")[2].split(":")[1].replace("}", ""));
        }

        String discountDetail;
    }
}
