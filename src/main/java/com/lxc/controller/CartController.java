package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
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

    @RequestMapping("delete/{bookId}")
    public String delete(@PathVariable("bookId") Integer id, HttpSession session){

        session.setAttribute("cart", updateCart(id, session, "delete"));
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("reset")
    public String reset(HttpSession session){

        Cart cart = (Cart)session.getAttribute("cart");
        cart.resetCart();
        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", cart.getTotalPrice());
        return "redirect:/cart/cartItems";
    }

    @RequestMapping("confirm")
    public String confirm(HttpSession session){

        session.setAttribute("totalPrice", ((Cart)session.getAttribute("cart")).getTotalPrice());
        return "redirect:/cart/cartItems";
    }

    private Cart updateCart(Integer id, HttpSession session, String operation){

        retrieveAndDo(id, ((Cart)session.getAttribute("cart")).getCartItems(), session, operation);
        return (Cart)session.getAttribute("cart");
    }

    private void retrieveAndDo(Integer id, List<CartItem> cartItems, HttpSession session, String operation){

        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()){
            CartItem item = iterator.next();
            if(item.getBook().getId() == id){
                if("increase".equals(operation)){
                    item.increaseQuantity();
                    decreaseStock(id, session);
                }else if("decrease".equals(operation)){
                    if(item.getQuantity() > 0){
                        item.decreaseQuantity();
                        increaseStock(id);
                    }
                }else if("delete".equals(operation)){
                    iterator.remove();
                }
            }
        }
    }

    private void decreaseStock(Integer id, HttpSession session){

        if(bookService.findById(id).getStock() >= 1){
            bookService.decreaseStock(bookService.findById(id));
        }else {
            session.setAttribute("msg", "库存不足");
        }
    }

    private void increaseStock(Integer id){

        bookService.increaseStock(bookService.findById(id));
    }
}
