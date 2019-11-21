package com.lxc.helper;

import com.lxc.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionUserManagerTest {

    @InjectMocks
    private SessionUserManager sessionUserManager;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {

        ReflectionTestUtils.setField(sessionUserManager, "SESSION_USER", "user");
    }

    @Test
    public void getCurrentUser_happyPath() {

        HttpSession session = mockSession();
        User user = mock(User.class);
        when(session.getAttribute("user")).thenReturn(user);

        assertThat(sessionUserManager.getCurrentUser()).isEqualTo(user);
    }

    @Test
    public void login_happyPath() {

        User user = mock(User.class);
        HttpSession session = mockSession();

        sessionUserManager.login(user);

        verify(session).setAttribute("user", user);
    }

    @Test
    public void login_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionUserManager, "request", null);

        sessionUserManager.login(mock(User.class));

        verify(request, never()).getSession();
    }

    @Test
    public void logout_happyPath() {

        HttpSession session = mockSession();

        sessionUserManager.logout();

        verify(session).removeAttribute("user");
    }

    @Test
    public void logout_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionUserManager, "request", null);

        sessionUserManager.logout();

        verify(request, never()).getSession();
    }

    @Test
    public void updateCart_happyPath() {

        User user = mock(User.class);
        HttpSession session = mockSession();

        sessionUserManager.updateUser(user);

        verify(session).setAttribute("user", user);
    }

    @Test
    public void updateCart_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionUserManager, "request", null);

        sessionUserManager.updateUser(mock(User.class));

        verify(request, never()).getSession();
    }

    private HttpSession mockSession() {

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        return session;
    }
}
