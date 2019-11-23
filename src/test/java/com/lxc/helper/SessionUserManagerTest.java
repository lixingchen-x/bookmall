package com.lxc.helper;

import com.lxc.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class SessionUserManagerTest {

    @InjectMocks
    private SessionUserManager sessionUserManager;

    @Mock
    private HttpSession session;

    @Before
    public void setUp() {

        ReflectionTestUtils.setField(sessionUserManager, "SESSION_USER", "user");
    }

    @Test
    public void getCurrentUser_happyPath() {

        User user = mock(User.class);
        when(session.getAttribute("user")).thenReturn(user);

        assertThat(sessionUserManager.getCurrentUser()).isEqualTo(user);
    }

    @Test
    public void login_happyPath() {

        User user = mock(User.class);

        sessionUserManager.login(user);

        verify(session).setAttribute("user", user);
    }

    @Test
    public void login_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionUserManager, "session", null);
        User user = mock(User.class);

        sessionUserManager.login(user);

        verify(session, never()).setAttribute("user", user);
    }

    @Test
    public void logout_happyPath() {

        sessionUserManager.logout();

        verify(session).removeAttribute("user");
    }

    @Test
    public void logout_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionUserManager, "session", null);

        sessionUserManager.logout();

        verify(session, never()).removeAttribute("user");
    }

    @Test
    public void updateCart_happyPath() {

        User user = mock(User.class);

        sessionUserManager.updateUser(user);

        verify(session).setAttribute("user", user);
    }
}
