package com.lxc.constants;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ResultEnumTest {

    @Test
    public void getMsg_happyPath() {

        assertThat(ResultEnum.SUCCESS.getMsg()).isEqualTo("success");
    }

    @Test
    public void getCode_happyPath() {

        assertThat(ResultEnum.SUCCESS.getCode()).isEqualTo(0);
    }
}
