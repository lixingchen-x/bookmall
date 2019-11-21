package com.lxc.result;

import com.lxc.constants.RegisterMsgEnum;
import com.lxc.constants.ResultEnum;
import com.lxc.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultsTest {

    @InjectMocks
    private Results results;

    @Test
    public void success_happyPath() {

        Result result = results.success();

        assertThat(result.getCode()).isEqualTo(ResultEnum.SUCCESS.getCode());
        assertThat(result.getMsg()).isEqualTo(ResultEnum.SUCCESS.getMsg());
    }

    @Test
    public void successWithData_happyPath() {

        Book book = new Book();
        Result result = results.success(book);

        assertThat(result.getCode()).isEqualTo(ResultEnum.SUCCESS.getCode());
        assertThat(result.getMsg()).isEqualTo(ResultEnum.SUCCESS.getMsg());
        assertThat(result.getData()).isEqualTo(book);
    }

    @Test
    public void fail_happyPath() {

        Result result = results.fail();

        assertThat(result.getCode()).isEqualTo(ResultEnum.FAIL.getCode());
        assertThat(result.getMsg()).isEqualTo(ResultEnum.FAIL.getMsg());
    }

    @Test
    public void registerSuccess_happyPath() {

        Result result = results.registerSuccess();

        assertThat(result.getCode()).isEqualTo(RegisterMsgEnum.REGISTER_SUCCESS.getCode());
        assertThat(result.getMsg()).isEqualTo(RegisterMsgEnum.REGISTER_SUCCESS.getMsg());
    }

    @Test
    public void usernameExists_happyPath() {

        Result result = results.usernameExists();

        assertThat(result.getCode()).isEqualTo(RegisterMsgEnum.USERNAME_EXISTS.getCode());
        assertThat(result.getMsg()).isEqualTo(RegisterMsgEnum.USERNAME_EXISTS.getMsg());
    }

    @Test
    public void passwordShort_happyPath() {

        Result result = results.passwordShort();

        assertThat(result.getCode()).isEqualTo(RegisterMsgEnum.PASSWORD_SHORT.getCode());
        assertThat(result.getMsg()).isEqualTo(RegisterMsgEnum.PASSWORD_SHORT.getMsg());
    }
}
