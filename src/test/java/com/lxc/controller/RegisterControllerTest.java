package com.lxc.controller;

import com.lxc.constants.RegisterMsgEnum;
import com.lxc.constants.Result;
import com.lxc.entity.User;
import com.lxc.exception.FailedSendingEmailException;
import com.lxc.service.UserService;
import com.lxc.utils.DefaultMailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import javax.mail.MessagingException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserService userService;

    @Mock
    private DefaultMailSender mailSender;

    @Test
    public void toRegister() {

        assertThat(registerController.register()).isEqualTo("register");
    }

    @Test
    public void doRegister_whenUsernameExists() throws FailedSendingEmailException {

        User user = User.builder().username("a").build();
        Model mockedModel = mock(Model.class);
        User mockedUser = mock(User.class);
        when(userService.findByUsername("a")).thenReturn(mockedUser);

        assertUsernameExists(registerController.doRegister(user, mockedModel));
    }

    @Test
    public void doRegister_whenPasswordLessThanSix() throws FailedSendingEmailException {

        User user = User.builder().username("a").password("123").build();
        Model mockedModel = mock(Model.class);
        when(userService.findByUsername("a")).thenReturn(null);

        assertPasswordLessThanSix(registerController.doRegister(user, mockedModel));
    }

    @Test(expected = FailedSendingEmailException.class)
    public void doRegister_shouldThrowException_whenEmailInvalid() throws FailedSendingEmailException, MessagingException {

        User user = User.builder().username("a").password("123456").email("123").build();
        Model mockedModel = mock(Model.class);
        when(userService.findByUsername("a")).thenReturn(null);
        doThrow(MessagingException.class).when(mailSender).send("123", "注册成功！");

        registerController.doRegister(user, mockedModel);
    }

    @Test
    public void doRegister_happyPath() throws FailedSendingEmailException, MessagingException {

        User user = User.builder().username("a").password("123456").email("123").build();
        Model mockedModel = mock(Model.class);
        when(userService.findByUsername("a")).thenReturn(null);

        assertRegisterSuccess(registerController.doRegister(user, mockedModel));

        verify(userService).saveAsCustomer(user);
        verify(mailSender).send(user.getEmail(), "注册成功！");
    }

    private void assertUsernameExists(Result result) {

        assertThat(result.getCode()).isEqualTo(RegisterMsgEnum.USERNAME_EXISTS.getCode());
        assertThat(result.getMsg()).isEqualTo(RegisterMsgEnum.USERNAME_EXISTS.getMsg());
    }

    private void assertPasswordLessThanSix(Result result) {

        assertThat(result.getCode()).isEqualTo(RegisterMsgEnum.PASSWORD_SHORT.getCode());
        assertThat(result.getMsg()).isEqualTo(RegisterMsgEnum.PASSWORD_SHORT.getMsg());
    }

    private void assertRegisterSuccess(Result result) {

        assertThat(result.getCode()).isEqualTo(RegisterMsgEnum.REGISTER_SUCCESS.getCode());
        assertThat(result.getMsg()).isEqualTo(RegisterMsgEnum.REGISTER_SUCCESS.getMsg());
    }
}
