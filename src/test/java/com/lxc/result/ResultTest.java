package com.lxc.result;

import com.lxc.entity.Book;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ResultTest {

    @Test
    public void getCode_happyPath() {

        Result result = new Result();

        result.setCode(1);

        assertThat(result.getCode()).isEqualTo(1);
    }

    @Test
    public void getMsg_happyPath() {

        Result result = new Result();

        result.setMsg("a");

        assertThat(result.getMsg()).isEqualTo("a");
    }

    @Test
    public void getData_happyPath() {

        Result result = new Result();
        Book book = new Book();

        result.setData(book);

        assertThat(result.getData()).isEqualTo(book);
    }
}
