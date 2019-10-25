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
    public String cart(){

        return "/user/cart.html";
    }

    /**
     * 添加图书到购物车
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("add")
    public String addToCart(@RequestParam(value = "bookId") Integer id, @RequestParam(defaultValue = "0") Integer page
            , HttpSession session, Model model){

        model.addAttribute("page", page);
        Book book = bookService.findById(id);
        Cart cart = (Cart)session.getAttribute("cart");
        // 购物车中没有任何书
        if(cart == null){
            return cartHasNothing(book, session);
        }
        for(CartItem item : cart.getCartItems()){
            // 购物车中已有此书
            if(item.getBook().getId() == book.getId()){
                return theBookExists(item, book, session, cart);
            }
        }
        //购物车中只有有其他的书
        return theBookAbsent(book, session, cart);
    }

    /**
     * 添加书籍进购物车的三种情况
     * 第一种，购物车为空
     */
    private String cartHasNothing(Book book, HttpSession session){

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(book, 1, book.getPrice()));
        decreaseStock(book, session);
        Cart cart = new Cart((User)session.getAttribute("user"), cartItems);
        session.setAttribute("cart", cart);
        return "forward:/book/find";
    }

    /**
     * 添加书籍进购物车的三种情况
     * 第二种，购物车中已有此书
     */
    private String theBookExists(CartItem item, Book book, HttpSession session, Cart cart){

        item.increaseQuantity(); //购物车里+1
        item.setSubTotal(item.getSubTotal()); //更新subTotal
        decreaseStock(book, session); // 库存-1
        updateCart(cart, session, cart.getCartItems());
        return "forward:/book/find";
    }

    /**
     * 添加书籍进购物车的三种情况
     * 第三种，购物车不为空，但还没有此书
     */
    private String theBookAbsent(Book book, HttpSession session, Cart cart){

        cart.getCartItems().add(new CartItem(book, 1, book.getPrice()));
        decreaseStock(book, session);
        updateCart(cart, session, cart.getCartItems());
        return "forward:/book/find";
    }

    /**
     * 与数据库同步减库存
     * @param book
     * @param session
     */
    private void decreaseStock(Book book, HttpSession session){

        if(book.getStock() >= 1){
            bookService.decreaseStock(book);
        }else {
            session.setAttribute("msg", "库存不足");
        }
    }

    /**
     * 更新购物车
     * @param cart
     * @param session
     * @param cartItems
     */
    private void updateCart(Cart cart, HttpSession session, List<CartItem> cartItems){

        cart.setCartItems(cartItems);
        session.setAttribute("cart", cart);
    }
}
