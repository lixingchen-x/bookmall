package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.entity.User;
import com.lxc.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    BookServiceImpl bookService;

    @RequestMapping("display")
    public String display(Model model, @RequestParam(defaultValue = "0") Integer page){

        Page<Book> bookPages = bookService.findAllByPage(page);
        List<Book> bookList=bookPages.getContent();
        model.addAttribute("bookList", bookList);
        model.addAttribute("totalPages", bookPages.getTotalPages());
        model.addAttribute("page", page);
        return "/user/main.html";
    }

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
    @RequestMapping("add/{bookId}")
    public String addToCart(@PathVariable("bookId") Integer id, HttpSession session){

        Book book = bookService.findById(id);
        User user = (User)session.getAttribute("user");
        Cart cart = (Cart)session.getAttribute("cart");
        // 购物车中没有书
        if(cart == null){
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(new CartItem(book, 1, book.getPrice()));
            decreaseStock(book, session);
            cart = new Cart(user, cartItems);
            session.setAttribute("cart", cart);
            return "redirect:/shopping/display";
        }
        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem item:cartItems){
            // 购物车中已有此书
            if(item.getBook().getId() == book.getId()){
                // 购物车中已有此书，增加1购买数量
                item.increaseQuantity(); //购物车里+1
                item.setSubTotal(item.getSubTotal()); //更新subTotal
                decreaseStock(book, session); // 库存-1
                updateCart(cart, session, cartItems);
                return "redirect:/shopping/display";
            }

        }
        //购物车中还没有此书
        cartItems.add(new CartItem(book, 1, book.getPrice()));
        decreaseStock(book, session);
        updateCart(cart, session, cartItems);
        return "redirect:/shopping/display";
    }

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
