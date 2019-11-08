package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private BookService bookService;

    @RequestMapping("cart")
    public String cart() {

        return "/user/cart.html";
    }

    /**
     * 添加图书到购物车，并同步更新购物车
     */
    @RequestMapping("add")
    public String addToCart(@RequestParam(value = "bookId") Integer id,
                            @RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(value = "condition", required = false) String condition,
                            @RequestParam(value = "keyword", required = false) String keyword, HttpServletRequest request) {

        request.setAttribute("page", page);
        request.setAttribute("condition", condition);
        request.setAttribute("keyword", keyword);
        Cart cart = (Cart)request.getSession().getAttribute("cart");
        bookService.decreaseStock(id);
        request.getSession().setAttribute("cart", cart.updateCart(createCartItem(id, 1)));
        if ("name".equals(condition)) {
            return "forward:/book/findByName";
        }else if ("author".equals(condition)) {
            return "forward:/book/findByAuthor";
        }else if ("isbn".equals(condition)) {
            return "forward:/book/findByIsbn";
        }else {
            return "forward:/book/books";
        }
    }

    private CartItem createCartItem(Integer id, Integer quantity) {

        Book book = bookService.findById(id);
        return CartItem.builder().book(book).quantity(quantity).build();
    }
}
