package com.lxc.result;

import com.lxc.constants.RegisterMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterResultsTest {

    @InjectMocks
    private RegisterResults registerResults;

    @Test
    public void registerSuccess_happyPath() {

        Result result = registerResults.registerSuccess();

        assertThat(result.getCode()).isEqualTo(RegisterMsg.REGISTER_SUCCESS.getCode());
    }

    @Test
    public void usernameExists_happyPath() {

        Result result = registerResults.usernameExists();

        assertThat(result.getCode()).isEqualTo(RegisterMsg.USERNAME_EXISTS.getCode());
    }

    @Test
    public void passwordShort_happyPath() {

        Result result = registerResults.passwordShort();

        assertThat(result.getCode()).isEqualTo(RegisterMsg.PASSWORD_SHORT.getCode());
    }
}
