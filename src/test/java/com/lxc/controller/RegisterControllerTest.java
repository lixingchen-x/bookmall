package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserService userService;

    @Test
    public void toRegister() {

        assertThat(registerController.register()).isEqualTo("register");
    }

    @Test
    public void doRegister_whenUsernameExists() {

        User user = User.builder().username("a").build();
        Model mockedModel = mock(Model.class);
        User mockedUser = mock(User.class);
        when(userService.findByUsername("a")).thenReturn(mockedUser);

        assertThat(registerController.doRegister(user, mockedModel)).isEqualTo("register");
    }

    @Test
    public void doRegister_whenPasswordLessThanSix() {

        User user = User.builder().username("a").password("123").build();
        Model mockedModel = mock(Model.class);
        when(userService.findByUsername("a")).thenReturn(null);

        assertThat(registerController.doRegister(user, mockedModel)).isEqualTo("register");
    }

    @Test
    public void doRegister_happyPath() {

        User user = User.builder().username("a").password("123456").build();
        Model mockedModel = mock(Model.class);
        when(userService.findByUsername("a")).thenReturn(null);

        assertThat(registerController.doRegister(user, mockedModel)).isEqualTo("login");

        verify(userService).saveAsCustomer(user);
    }
}
