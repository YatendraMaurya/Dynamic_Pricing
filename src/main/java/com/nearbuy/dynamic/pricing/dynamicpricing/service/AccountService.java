package com.nearbuy.dynamic.pricing.dynamicpricing.service;

import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.AccountServiceModel;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.BookingResponse;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private static final Logger logger=LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AppProperties env;

    @Autowired
    private AppRestClient client;

    public AccountServiceModel getAccountDetails(Long id){
        String url= env.getProperty("accountservices")+id;
        logger.info(url);
        ResponseEntity<String> resp = client.fireGet(url,null,null);
        if (resp.getStatusCode().is2xxSuccessful()) {
            logger.info(AppUtil.getFromJson(resp.getBody(), AccountServiceModel.class).toString());
            return AppUtil.getFromJson(resp.getBody(), AccountServiceModel.class);
        } else {
            logger.error("Error in getting booking Details for bookingId {}",id);
            return null;
        }
    }

    public List<Long> getDecisonMaker(Long id) {
        List<Long> res = new ArrayList<>();
            AccountServiceModel accountServiceModel = getAccountDetails(id);
            List<AccountServiceModel.AccountUser> userList = accountServiceModel.getRs().getAccountUsers();
            for (AccountServiceModel.AccountUser user : userList) {
                if (user.getRole().getRoleName().equalsIgnoreCase("Decision Maker")) {
                    res.add(user.getId());
                }
            }
        return res;
    }
}
