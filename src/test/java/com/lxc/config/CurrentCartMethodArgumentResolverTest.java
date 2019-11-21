package com.lxc.config;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.helper.CartManager;
import com.lxc.helper.CurrentCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrentCartMethodArgumentResolverTest {

    @InjectMocks
    private CurrentCartMethodArgumentResolver resolver;

    @Mock
    private CartManager cartManager;

    @Test
    public void supportsParameter_happyPath() {

        MethodParameter parameter = createParameter(Cart.class, true);
        assertThat(resolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    public void supportsParameter_shouldFalse_ifClassWrong() {

        MethodParameter parameter = createParameter(Book.class, true);
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void supportsParameter_shouldFalse_ifAnnotationWrong() {

        MethodParameter parameter = createParameter(Cart.class, false);
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void supportsParameter_shouldFalse_ifBothWrong() {

        MethodParameter parameter = createParameter(Book.class, false);
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void resolveArgument_happyPath() {

        MethodParameter parameter = createParameter(Cart.class, true);
        Cart cart = mock(Cart.class);
        when(cartManager.getCurrentCart()).thenReturn(cart);

        assertThat(resolver.resolveArgument(parameter, mock(ModelAndViewContainer.class),
                mock(NativeWebRequest.class), mock(WebDataBinderFactory.class)))
                .isEqualTo(cart);
    }

    private MethodParameter createParameter(Class<?> className, boolean trueOrFalse) {

        MethodParameter parameter = mock(MethodParameter.class);
        doReturn(className).when(parameter).getParameterType();
        when(parameter.hasParameterAnnotation(CurrentCart.class)).thenReturn(trueOrFalse);
        return parameter;
    }
}
