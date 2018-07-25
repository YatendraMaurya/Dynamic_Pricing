package com.nearbuy.dynamic.pricing.dynamicpricing.util;

public class AppConstants {
    public static final String PAL_EVENT = "T.SUCCESSFUL_PURCHASE";
    public static final String UPDATE_EVENT = "T.INVENTORY_UPDATE";
    public static final String CREATE_EVENT = "T.INVENTORY_CREATE";
    public static final long SEC = 1000;
    public static final long MINUTE = SEC * 60;
    public static final String MEDIUM_PUSH = "2" ;
    public static final long RADIUS = 50l;

    public static final class NOTIFICATION_STATUS {
        public static final Integer FAILED = 1;
        public static final Integer SUCCESS = 2;
        public static final Integer OPENED = 3;
    }
}
