package com.lxc.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Cart implements Serializable {

    private User user;
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return getUser().equals(cart.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser());
    }

    public Double getTotalPrice() {

        return cartItems.stream().mapToDouble(CartItem :: getSubTotal).sum();
    }

    public void resetCart() {

        cartItems.clear();
    }

    public CartItem getByBookId(Integer id) {

        return cartItems.stream().filter(cartItem -> cartItem.getBookId().equals(id))
                .findFirst().orElse(null);
    }

    public void removeCartItem(Integer id) {

        CartItem cartItem = getByBookId(id);
        if (cartItem != null) {
            cartItems.remove(cartItem);
        }
    }

    public void decreaseQuantity(Integer id) {

        CartItem cartItem = getByBookId(id);
        if (cartItem != null) {
            cartItem.decreaseQuantity();
        }
    }

    public void increaseQuantity(Integer id) {

        CartItem cartItem = getByBookId(id);
        if (cartItem != null) {
            cartItem.increaseQuantity();
        }
    }

    public boolean contains(Integer id) {

        return getByBookId(id) != null;
    }

    public void updateCart(CartItem cartItem) {

        CartItem item = this.getByBookId(cartItem.getBookId());
        if (item != null) {
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            return;
        }
        cartItems.add(cartItem);
    }

    public Integer getQuantity(Integer bookId) {

        return this.getByBookId(bookId).getQuantity();
    }

    public void addBook(Book book, Integer quantity) {

        CartItem cartItem = CartItem.builder().book(book).quantity(quantity).build();
        updateCart(cartItem);
    }
}
