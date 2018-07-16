package com.nearbuy.dynamic.pricing.dynamicpricing.util;

import com.nearbuy.dynamic.pricing.dynamicpricing.TestConfig;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.MerchantService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.MerchantDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.awt.SunHints;

import java.util.List;

@RunWith(value = SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
public class MerchantTest {
    @Autowired
    MerchantService merchantService;

    public static final Logger logger = LoggerFactory.getLogger(AppRestClientTest.class);

    @Test
    public void TsetMerchantService() {
        merchantService.getMerchant(100002l);


    }


}
