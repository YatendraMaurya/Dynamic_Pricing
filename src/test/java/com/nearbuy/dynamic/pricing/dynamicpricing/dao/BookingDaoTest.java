package com.nearbuy.dynamic.pricing.dynamicpricing.dao;

import com.nearbuy.dynamic.pricing.dynamicpricing.dao.model.PalBooking;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingDaoTest {

    @Autowired
    BookingDao dao;

    @Test
    public void t1insert(){
        PalBooking pb = new PalBooking();
        pb.setMerchantid(1L);
        dao.addBooking(pb);
        PalBooking pb2 = new PalBooking();
        pb2.setMerchantid(2L);
        dao.addBooking(pb2);
    }

    @Test
    public void t2getBookingCount(){
        List<Long> mids = Arrays.asList(1L, 2L);
        HashMap<Long, Long> v = dao.getBookingCount(mids);
        Assert.assertNotNull(v.get(1));
        Assert.assertNotNull(v.get(2));
    }
}
