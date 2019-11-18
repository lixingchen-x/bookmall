package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.helper.CurrentCart;
import com.lxc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("cartItems")
    public String cartItems() {

        return "user/cart.html";
    }

    @RequestMapping("increase/{bookId}")
    public String increase(@PathVariable("bookId") Integer id, @CurrentCart Cart cart) {

        cartService.increaseQuantity(id, cart);
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("decrease/{bookId}")
    public String decrease(@PathVariable("bookId") Integer id, @CurrentCart Cart cart) {

        cartService.decreaseQuantity(id, cart);
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("delete/{bookId}")
    public String delete(@PathVariable("bookId") Integer id, @CurrentCart Cart cart) {

        cartService.deleteBook(id, cart);
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("reset")
    public String reset(@CurrentCart Cart cart) {

        cartService.reset(cart);
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("toBuy")
    public String confirm() {

        return "redirect:/book/books";
    }
}
