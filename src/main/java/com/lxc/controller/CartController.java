package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private BookService bookService;

    /**
     * 购物车中书籍展示
     * @return
     */
    @RequestMapping("cartItems")
    public String cartItems() {

        return "user/cart.html";
    }

    /**
     * 购物车书籍数量加1
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("increase/{bookId}")
    public String increase(@PathVariable("bookId") Integer id, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");
        cart.increaseQuantity(id);
        bookService.decreaseStock(id);
        session.setAttribute("cart", cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 购物车书籍数量减1
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("decrease/{bookId}")
    public String decrease(@PathVariable("bookId") Integer id, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");
        cart.decreaseQuantity(id);
        bookService.increaseStock(id);
        session.setAttribute("cart", cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 从购物车中删除此书
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("delete/{bookId}")
    public String delete(@PathVariable("bookId") Integer id, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart");
        cart.removeCartItem(id);
        session.setAttribute("cart", cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 清空购物车
     * @param session
     * @return
     */
    @RequestMapping("reset")
    public String reset(HttpSession session) {

        Cart cart = (Cart)session.getAttribute("cart");
        cart.resetCart();
        session.setAttribute("cart", cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 去挑选图书
     * @param session
     * @return
     */
    @RequestMapping("toBuy")
    public String confirm(HttpSession session) {

        session.setAttribute("key", null);
        session.setAttribute("keyword", null);
        //ToDo
        return "redirect:/book/books";
    }
}
