package com.lxc.helper;

import com.lxc.entity.Cart;
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
public class SessionCartManagerTest {

    @InjectMocks
    private SessionCartManager sessionCartManager;

    @Mock
    private UserManager userManager;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {

        ReflectionTestUtils.setField(sessionCartManager, "SESSION_CART", "cart");
    }

    @Test
    public void getCurrentCart_happyPath() {

        HttpSession session = mockSession();
        Cart cart = mock(Cart.class);
        when(session.getAttribute("cart")).thenReturn(cart);

        assertThat(sessionCartManager.getCurrentCart()).isEqualTo(cart);
    }

    @Test
    public void initCart_happyPath() {

        Cart cart = createCart();
        HttpSession session = mockSession();

        sessionCartManager.initCart();

        verify(session).setAttribute("cart", cart);
    }

    @Test
    public void initCart_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionCartManager, "request", null);

        sessionCartManager.initCart();

        verify(request, never()).getSession();
    }

    @Test
    public void updateCart_happyPath() {

        Cart cart = createCart();
        HttpSession session = mockSession();

        sessionCartManager.updateCart(cart);

        verify(session).setAttribute("cart", cart);
    }

    @Test
    public void updateCart_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionCartManager, "request", null);

        sessionCartManager.updateCart(mock(Cart.class));

        verify(request, never()).getSession();
    }

    private HttpSession mockSession() {

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        return session;
    }

    private Cart createCart() {

        User user = mockUser();
        return new Cart(user);
    }

    private User mockUser() {

        User user = mock(User.class);
        when(userManager.getCurrentUser()).thenReturn(user);
        return user;
    }
}
