package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.helper.CartManager;
import com.lxc.service.BookService;
import com.lxc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private BookService bookService;

    @Autowired
    private CartManager cartManager;

    @Override
    public void increaseQuantity(Integer bookId, Cart cart) {

        cart.increaseQuantity(bookId);
        cartManager.updateCart(cart);
    }

    @Override
    public void decreaseQuantity(Integer bookId, Cart cart) {

        cart.decreaseQuantity(bookId);
        cartManager.updateCart(cart);
    }

    @Override
    public void deleteBook(Integer bookId, Cart cart) {

        cart.removeCartItem(bookId);
        cartManager.updateCart(cart);
    }

    @Override
    public void reset(Cart cart) {

        cart.resetCart();
        cartManager.updateCart(cart);
    }

    @Override
    public CartItem createCartItem(Integer id, Integer quantity) {

        Book book = bookService.findById(id);
        return CartItem.builder().book(book).quantity(quantity).build();
    }
}
