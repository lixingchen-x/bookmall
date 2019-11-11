package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.helper.AttributesHelper;
import com.lxc.helper.CurrentCart;
import com.lxc.service.BookService;
import com.lxc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AttributesHelper attributesHelper;

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
     * @param cart
     * @return
     */
    @RequestMapping("increase/{bookId}")
    public String increase(@PathVariable("bookId") Integer id, @CurrentCart Cart cart) {

        cart.increaseQuantity(id);
        bookService.decreaseStock(id);
        attributesHelper.updateCart(cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 购物车书籍数量减1
     * @param id
     * @param cart
     * @return
     */
    @RequestMapping("decrease/{bookId}")
    public String decrease(@PathVariable("bookId") Integer id, @CurrentCart Cart cart) {

        cart.decreaseQuantity(id);
        bookService.increaseStock(id, 1);
        attributesHelper.updateCart(cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 从购物车中删除此书
     * @param id
     * @param cart
     * @return
     */
    @RequestMapping("delete/{bookId}")
    public String delete(@PathVariable("bookId") Integer id, @CurrentCart Cart cart) {

        bookService.increaseStock(id, cart.getByBookId(id).getQuantity());
        cart.removeCartItem(id);
        attributesHelper.updateCart(cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 清空购物车
     * @param cart
     * @return
     */
    @RequestMapping("reset")
    public String reset(@CurrentCart Cart cart) {

        cartService.rollBackStockForCartReset(cart);
        cart.resetCart();
        attributesHelper.updateCart(cart);
        return "redirect:/cart/cartItems";
    }

    /**
     * 去挑选图书
     * @return
     */
    @RequestMapping("toBuy")
    public String confirm() {

        return "redirect:/book/books";
    }
}
