package com.lxc.constants;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class OrderStatusEnumTest {

    @Test
    public void getMsg_happyPath() {

        assertThat(OrderStatusEnum.PAID.getMsg()).isEqualTo("PAID");
    }
}
