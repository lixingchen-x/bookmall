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
        // 购物车中没有任何书
        if (cart == null) {
            return addToEmptyCart(bookService.findById(id), session);
        }
        //购物车中已有此书
        if (cart.contains(id)) {
            return addOneToThisBook(id, session, cart);
        }
        //购物车中有其他的书,暂无此书
        return addFirstOneToCart(bookService.findById(id), session, cart);
    }

    /**
     * 添加书籍进购物车的三种情况
     * 第一种，购物车为空
     */
    private String addToEmptyCart(Book book, HttpSession session) {

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(book, 1));
        decreaseStock(book);
        Cart cart = new Cart((User)session.getAttribute("user"), cartItems);
        session.setAttribute("cart", cart);
        return "forward:/book/find";
    }

    /**
     * 添加书籍进购物车的三种情况
     * 第二种，购物车中已有此书
     */
    private String addOneToThisBook(Integer id, HttpSession session, Cart cart) {

        cart.increaseQuantity(id); //购物车里+1
        decreaseStock(bookService.findById(id)); // 库存-1
        session.setAttribute("cart", cart);
        return "forward:/book/find";
    }

    /**
     * 添加书籍进购物车的三种情况
     * 第三种，购物车不为空，但还没有此书
     */
    private String addFirstOneToCart(Book book, HttpSession session, Cart cart) {

        cart.getCartItems().add(new CartItem(book, 1));
        decreaseStock(book);
        session.setAttribute("cart", cart);
        return "forward:/book/find";
    }

    /**
     * 与数据库同步减库存
     * @param book
     */
    private void decreaseStock(Book book) {

        bookService.decreaseStock(book.getId());
    }
}
