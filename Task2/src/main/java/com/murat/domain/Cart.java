package com.murat.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public int getQuantityInCart(Product product) {

        CartItem item = findItem(product);

        if (item == null) {
            return 0;
        }

        return item.getQuantity();
    }

    public int getRemainingStock(Product product) {
        return product.getStock() - getQuantityInCart(product);
    }

    // if the quantity is invalid or the total quantity more than stock.
    public boolean add(Product product, int quantity) {

        if (quantity < 1 || quantity > getRemainingStock(product)) {

            //noting to add in cart!
            return false;
        }

        CartItem item = findItem(product);

        if (item == null) {
            items.add(new CartItem(product, quantity));
        } else {
            item.increaseQuantity(quantity);
        }

        return true;
    }

    public boolean contains(Product product) {
        return findItem(product) != null;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private CartItem findItem(Product product) {

        for (CartItem item : items) {
            if (item.getProduct() == product) {
                return item;
            }
        }

        return null;
    }
}