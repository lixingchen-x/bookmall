package com.lxc.entity;

import com.lxc.testUtils.DateUtils;
import org.junit.Test;
import java.text.ParseException;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class OrderTest {

    @Test
    public void getCreateDate_happyPath() throws ParseException {

        Order order = new Order();
        Date date = DateUtils.DateConvertor("2019-10-29");
        order.setCreateDate(date);
        assertThat(order.getCreateDate(), is(date));
    }
}
