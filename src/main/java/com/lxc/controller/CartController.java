package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    BookServiceImpl bookService;

    @RequestMapping("cartItems")
    public String cartItems(){

        return "user/cart.html";
    }

    @RequestMapping("increase/{bookId}")
    public String increase(@PathVariable("bookId") Integer id, HttpSession session){

        session.setAttribute("cart", updateCart(id, session, "increase"));
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("decrease/{bookId}")
    public String decrease(@PathVariable("bookId") Integer id, HttpSession session){

        session.setAttribute("cart", updateCart(id, session, "decrease"));
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("confirm")
    public String confirm(HttpSession session){

        Cart cart = (Cart)session.getAttribute("cart");
        Double totalPrice = cart.getTotalPrice();
        session.setAttribute("totalPrice", "商品总价是： "+totalPrice);
        return "redirect:/cart/cartItems";
    }

    private Cart updateCart(Integer id, HttpSession session, String operation){

        Cart cart = (Cart)session.getAttribute("cart");
        List<CartItem> cartItems = cart.getCartItems();
        retrieveAndDo(id, cartItems, session, operation);
        return cart;
    }

    private void retrieveAndDo(Integer id, List<CartItem> cartItems, HttpSession session, String operation){

        for(CartItem cartItem:cartItems){
            if(cartItem.getBook().getId() == id){
                if("increase".equals(operation)){
                    cartItem.increaseQuantity();
                    decreaseStock(id, session);
                }else if("decrease".equals(operation)){
                    if(cartItem.getQuantity() > 0){
                        cartItem.decreaseQuantity();
                        increaseStock(id);
                    }
                }
            }
        }
    }

    private void decreaseStock(Integer id, HttpSession session){

        Book oldBook = bookService.findById(id);
        if(oldBook.getStock() >= 1){
            bookService.decreaseStock(oldBook);
        }else {
            session.setAttribute("msg", "库存不足");
        }
    }

    private void increaseStock(Integer id){

        Book oldBook = bookService.findById(id);
        bookService.increaseStock(oldBook);
    }
    // ToDo 从购物车删除商品 & 清空购物车
}
