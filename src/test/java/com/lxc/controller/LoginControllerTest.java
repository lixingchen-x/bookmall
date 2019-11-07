package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private SecurityManager securityManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private MockHttpSession session;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setViewResolvers(resolver).build();
    }

    @Test
    public void toLogin_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andReturn();
    }

    @Test
    public void toIndex_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }

    @Test
    public void doLogin_happyPath() {

        User valid = User.builder().username("a").password("123456").build();
        User unchecked = User.builder().username("a").password("123456").build();
        Subject subject = createMockedSubject();
        when(securityManager.createSubject(any())).thenReturn(subject);
        when(userService.findByUsername("a")).thenReturn(valid);
        when(request.getSession()).thenReturn(session);
        assertThat(loginController.doLogin(request, unchecked)).isEqualTo("index");
    }

    @Test
    public void doLogin_shouldFail_ifUsernameDoesNotExist() {

        User unchecked = User.builder().username("a").password("123456").build();
        Subject subject = createMockedSubject();
        when(securityManager.createSubject(any())).thenReturn(subject);
        doThrow(new UnknownAccountException()).when(subject).login(any(UsernamePasswordToken.class));
        assertThat(loginController.doLogin(request, unchecked)).isEqualTo("login");
    }

    @Test
    public void doLogin_shouldFail_ifPasswordIsWrong() {

        User unchecked = User.builder().username("a").password("123456").build();
        Subject subject = createMockedSubject();
        when(securityManager.createSubject(any())).thenReturn(subject);
        doThrow(new IncorrectCredentialsException()).when(subject).login(any(UsernamePasswordToken.class));
        assertThat(loginController.doLogin(request, unchecked)).isEqualTo("login");
    }

    @Test
    public void doLogout_happyPath() {

        Subject subject = createMockedSubject();
        when(securityManager.createSubject(any())).thenReturn(subject);
        assertThat(loginController.doLogout()).isEqualTo("login");
    }

    private Subject createMockedSubject() {

        ThreadContext.remove();
        SecurityUtils.setSecurityManager(securityManager);
        return mock(Subject.class);
    }
}
