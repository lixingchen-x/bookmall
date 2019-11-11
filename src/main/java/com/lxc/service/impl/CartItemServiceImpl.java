package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.entity.CartItem;
import com.lxc.service.BookService;
import com.lxc.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private BookService bookService;

    @Override
    public CartItem createCartItem(Integer id, Integer quantity) {

        Book book = bookService.findById(id);
        return CartItem.builder().book(book).quantity(quantity).build();
    }
}
