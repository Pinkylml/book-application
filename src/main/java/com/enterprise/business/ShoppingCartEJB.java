package com.enterprise.business;

import com.enterprise.domain.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;

@Stateful // Associated with one and only one client
@StatefulTimeout(20000) // Removes the bean from memory if idle for over 20 seconds
public class ShoppingCartEJB {
    private static final long serialVersionUID = 1L;
    private final List<Item> cartItems = new ArrayList<>();

    public void addItem(Item item) {
        if (!cartItems.contains(item)) {
            cartItems.add(item);
        }
    }

    public void removeItem(Item item) {
        if (cartItems.contains(item)) {
            cartItems.remove(item);
        }
    }

    public Float getTotal() {
        if (cartItems == null || cartItems.isEmpty()) {
            return 0f;
        }
        Float total = 0f;
        for (Item cardItem : cartItems) {
            total += cardItem.getPrice();
        }
        return total;
    }

    @Remove
    public void checkout() {
        System.out.println("--> [Stateful EJB] Checking out "
                + cartItems.size()
                + " items. Cleaning session state.");
        cartItems.clear();
    }

    @Remove // Explicit cleanup method requested by clients who abandon their session
    public void empty() {
        cartItems.clear(); //
    }
}
