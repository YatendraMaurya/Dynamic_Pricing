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
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private static final Logger logger=LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AppProperties env;

    @Autowired
    private AppRestClient client;

    public AccountServiceModel getAccountDetails(Long id){
        logger.info("getAccountDetails: entering with id : {}", id);
        String url= env.getProperty("account.service")+"/accounts/"+id;
        logger.info(url);
        ResponseEntity<String> resp = client.fireGet(url,null,null);
        if (resp.getStatusCode().is2xxSuccessful()) {
           // logger.info(AppUtil.getFromJson(resp.getBody(), AccountServiceModel.class).toString());
            return AppUtil.getFromJson(resp.getBody(), AccountServiceModel.class);
        } else {
            logger.error("Error in getting booking Details for bookingId {}",id);
            return null;
        }
    }

    public List<Long> getDecisonMakerT(Long id) {
        List<Long> res = new ArrayList<>();
            AccountServiceModel accountServiceModel = getAccountDetails(id);
            List<AccountServiceModel.AccountUser> userList = accountServiceModel.getRs().getAccountUsers();
            logger.info(userList.toString());
            for (AccountServiceModel.AccountUser user : userList) {
                if(user.getUserDevice().length>0){
                    for(AccountServiceModel.UserDevice userDevice : user.getUserDevice()){
                        //logger.info(user.getRole().getRoleName());
                       // logger.info("User appversion " +Integer.parseInt(userDevice.getAppVersion().split("_")[1])
                       // + " Useros  " + userDevice.getOs());
                        if (user.getRole().getRoleName().equalsIgnoreCase("Decision Maker")&
                            userDevice.getOs().equalsIgnoreCase("app_android")&
                            Integer.parseInt(userDevice.getAppVersion().split("_")[1])>=17) {
                                res.add(user.getId());
                        }
                    }
            }}
            logger.info(res.toString());
        return res;
    }
    private boolean isAdminOrPalManager(AccountServiceModel.AccountUser accountUser) {
        return "Decision Maker".equalsIgnoreCase(accountUser.getRole().getRoleName()) || "PAL Manager".equalsIgnoreCase(accountUser.getRole().getRoleName());
    }

    private boolean isCBIncreaseNotificationSupported(AccountServiceModel.UserDevice userDevice) {
        return Integer.parseInt(userDevice.getAppVersion().split("_")[1])>=17 && "app_android".equalsIgnoreCase(userDevice.getOs());
    }

    public List<Long> getDecisonMaker(Long id) {
        return getAccountDetails(id).getRs().getAccountUsers().stream().filter(accountUser -> isAdminOrPalManager(accountUser))
                .filter(accountUser -> Arrays.stream(accountUser.getUserDevice()).filter(userDevice -> isCBIncreaseNotificationSupported(userDevice)).count()>0)
                .map(AccountServiceModel.AccountUser::getId).collect(Collectors.toList());
    }


}
