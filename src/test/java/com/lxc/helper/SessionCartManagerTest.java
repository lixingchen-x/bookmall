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
    private HttpSession session;

    @Before
    public void setUp() {

        ReflectionTestUtils.setField(sessionCartManager, "SESSION_CART", "cart");
    }

    @Test
    public void getCurrentCart_happyPath() {

        Cart cart = mock(Cart.class);
        when(session.getAttribute("cart")).thenReturn(cart);

        assertThat(sessionCartManager.getCurrentCart()).isEqualTo(cart);
    }

    @Test
    public void initCart_happyPath() {

        Cart cart = mockCart();

        sessionCartManager.initCart();

        verify(session).setAttribute("cart", cart);
    }

    @Test
    public void initCart_shouldDoNothing_ifRequestIsNull() {

        ReflectionTestUtils.setField(sessionCartManager, "session", null);
        Cart cart = mockCart();

        sessionCartManager.initCart();

        verify(session, never()).setAttribute("cart", cart);
    }

    @Test
    public void updateCart_happyPath() {

        Cart cart = mockCart();

        sessionCartManager.updateCart(cart);

        verify(session).setAttribute("cart", cart);
    }

    private Cart mockCart() {

        return new Cart(mockUser());
    }

    private User mockUser() {

        User user = mock(User.class);
        when(userManager.getCurrentUser()).thenReturn(user);
        return user;
    }
}
