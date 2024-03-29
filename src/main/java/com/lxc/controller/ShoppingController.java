package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.helper.CurrentCart;
import com.lxc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    private final String CONDITION_NAME = "name";
    private final String CONDITION_AUTHOR = "author";
    private final String CONDITION_ISBN = "isbn";

    @Autowired
    private CartService cartService;

    @RequestMapping("cart")
    public String cart() {

        return "/user/cart.html";
    }

    @RequestMapping("add")
    public String addToCart(@RequestParam(value = "bookId") Integer id,
                            @RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(required = false) String condition,
                            @RequestParam(required = false) String keyword, Model model,
                            @CurrentCart Cart cart) {

        cart.updateCart(cartService.createCartItem(id, 1));
        model.addAttribute("page", page);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
        if (CONDITION_NAME.equals(condition)) {
            return "forward:/book/findByName";
        } else if (CONDITION_AUTHOR.equals(condition)) {
            return "forward:/book/findByAuthor";
        } else if (CONDITION_ISBN.equals(condition)) {
            return "forward:/book/findByIsbn";
        } else {
            return "forward:/book/books";
        }
    }
}
