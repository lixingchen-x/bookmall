package com.lxc.constants;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class RegisterMsgTest {

    @Test
    public void getCode_happyPath() {

        assertThat(RegisterMsg.REGISTER_SUCCESS.getCode()).isEqualTo(2);
    }
}
