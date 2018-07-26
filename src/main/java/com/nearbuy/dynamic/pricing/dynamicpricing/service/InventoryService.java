package com.nearbuy.dynamic.pricing.dynamicpricing.service;
import com.nearbuy.dynamic.pricing.dynamicpricing.service.model.InventoryServiceModel;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppProperties;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppRestClient;
import com.nearbuy.dynamic.pricing.dynamicpricing.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private static final Logger logger=LoggerFactory.getLogger(InventoryServiceModel.class);

    @Autowired
    AppProperties env;

    @Autowired
    private AppRestClient client;

    public InventoryServiceModel getInventoryDetails(long itemid,int itemType,String fromDate,String toDate){
        String url = env.getProperty("inventory.base")+"?itemId="+itemid+"&itemType="+itemType+"&fromDate="+fromDate+"&toDate="+toDate+"&isActive=true&inventoryTypeId=1";
       // logger.info(url);
        ResponseEntity<String> resp = client.fireGet(url,null,null);
        if (resp.getStatusCode().is2xxSuccessful()) {
            logger.info(AppUtil.getFromJson(resp.getBody(), InventoryServiceModel.class).toString());
            return AppUtil.getFromJson(resp.getBody(), InventoryServiceModel.class);
        } else {
            logger.error("Error in getting inventory Details for bookingId {}",itemid);
            return null;
        }
    }


}
