package com.lxc.config;

import com.lxc.entity.Cart;
import com.lxc.helper.CartManager;
import com.lxc.helper.CurrentCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component("CartMethodArgumentResolver")
public class CurrentCartMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private CartManager cartManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterType().isAssignableFrom(Cart.class)
                && parameter.hasParameterAnnotation(CurrentCart.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        return cartManager.getCurrentCart();
    }
}
