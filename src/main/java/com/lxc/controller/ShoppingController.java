package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.entity.User;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private BookServiceImpl bookService;

    @RequestMapping("cart")
    public String cart() {

        return "/user/cart.html";
    }

    /**
     * 添加图书到购物车
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("add")
    public String addToCart(@RequestParam(value = "bookId") Integer id,
                            @RequestParam(defaultValue = "0") Integer page, HttpSession session, Model model) {

        model.addAttribute("page", page);
        Cart cart = (Cart)session.getAttribute("cart");
        cart.addCartItem(createCartItem(id, 1));
        bookService.decreaseStock(id);
        session.setAttribute("cart", cart);
        return "forward:/book/find";
    }

    private CartItem createCartItem(Integer id, Integer quantity) {

        Book book = bookService.findById(id);
        return CartItem.builder().book(book).quantity(quantity).build();
    }
}
