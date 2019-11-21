package com.lxc.constants;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class RegisterMsgEnumTest {

    @Test
    public void getMsg_happyPath() {

        assertThat(RegisterMsgEnum.REGISTER_SUCCESS.getMsg()).isEqualTo("REGISTER_SUCCESS");
    }

    @Test
    public void getCode_happyPath() {

        assertThat(RegisterMsgEnum.REGISTER_SUCCESS.getCode()).isEqualTo(2);
    }
}
