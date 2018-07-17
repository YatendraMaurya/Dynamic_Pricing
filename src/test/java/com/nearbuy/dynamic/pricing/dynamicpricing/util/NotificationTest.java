package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.TestConfig;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.NotificationService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
public class NotificationTest {

    @Autowired
    NotificationService t1;

    @Test
    public void isOpened(){
        Assert.assertEquals(t1.getNotificationStatus(81l), AppConstants.NOTIFICATION_STATUS.FAILED);
    }
}
