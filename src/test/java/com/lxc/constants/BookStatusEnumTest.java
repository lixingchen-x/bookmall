package com.lxc.constants;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BookStatusEnumTest {

    @Test
    public void getMsg_happyPath() {

        assertThat(BookStatusEnum.AVAILABLE.getMsg()).isEqualTo("AVAILABLE");
    }
}
