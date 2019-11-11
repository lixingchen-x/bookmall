package com.lxc.service.impl;

import com.lxc.entity.Cart;
import com.lxc.service.BookService;
import com.lxc.service.CartService;
import com.lxc.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void rollBackStockForCartReset(Cart cart) {

        cart.getCartItems().forEach(cartItem ->
                bookService.increaseStock(cartItem.getBook().getId(), cartItem.getQuantity()));
    }

    @Override
    public void saveOrderItem(Cart cart, Integer orderId) {

        cart.getCartItems().forEach(cartItem -> orderItemService.save(cartItem.transferToOrderItem(orderId)));
    }
}
