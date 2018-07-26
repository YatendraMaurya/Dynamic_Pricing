package com.nearbuy.dynamic.pricing.dynamicpricing.dao;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.AccountService;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AccountServiceModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void test(){
//        AccountServiceModel account = accountService.getAccountDetails(1037337L);
        List<Long> uids = accountService.getDecisonMaker(1037337L);
        Assert.assertFalse(uids.isEmpty());
    }
}
