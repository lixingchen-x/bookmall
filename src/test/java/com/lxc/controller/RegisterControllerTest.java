package com.lxc.controller;

import com.lxc.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private UserServiceImpl userService;

    @Test
    public void register_happyPath() {


    }
}
