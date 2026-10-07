package com.murat.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DiscountCalculator {

    // Calculates the cart total after discounts and returns it.
    public static BigDecimal comparativeDiscount(Cart cart) {

        List<CartItem> cartItems = cart.getItems();

        int length = cartItems.size();

        BigDecimal finalCartPrice = BigDecimal.ZERO;

        if (length == 0) {
            return finalCartPrice;
        }

        int counter = 0;

        while (counter < length - 1) {

            CartItem firstItem = cartItems.get(counter);
            CartItem secondItem = cartItems.get(counter + 1);

            BigDecimal firstLineTotal = firstItem.getProduct().getUnitPrice()
                    .multiply(BigDecimal.valueOf(firstItem.getQuantity()));

            int result = firstItem.getProduct().getUnitPrice().compareTo(secondItem.getProduct().getUnitPrice());

            if (result > 0) {

                BigDecimal unitDiscount = giveDiscount(secondItem);

                BigDecimal lineDiscount = unitDiscount.multiply(BigDecimal.valueOf(firstItem.getQuantity()));

                BigDecimal discountedLineTotal = firstLineTotal.subtract(lineDiscount);

                System.out.println("Discount applied on " + firstItem.getProduct().getName()
                        + " because it is more expensive than " + secondItem.getProduct().getName()
                        + ". Discount: " + format(lineDiscount)
                        + " (" + format(firstLineTotal) + " -> " + format(discountedLineTotal) + ")");

                finalCartPrice = finalCartPrice.add(discountedLineTotal);

            } else {

                System.out.println("No discount on " + firstItem.getProduct().getName()
                        + " because it is not more expensive than " + secondItem.getProduct().getName() + ".");

                finalCartPrice = finalCartPrice.add(firstLineTotal);
            }

            counter += 1;
        }

        // The last item never gets a discount
        CartItem lastItem = cartItems.get(counter);

        finalCartPrice = finalCartPrice.add(lastItem.getProduct().getUnitPrice()
                .multiply(BigDecimal.valueOf(lastItem.getQuantity())));

        return finalCartPrice;
    }

    // Returns the total discount of one cart line (0 for the last line)
    public static BigDecimal getLineDiscount(List<CartItem> items, int index) {

        if (index >= items.size() - 1) {
            return BigDecimal.ZERO;
        }

        CartItem firstItem = items.get(index);
        CartItem secondItem = items.get(index + 1);

        int result = firstItem.getProduct().getUnitPrice().compareTo(secondItem.getProduct().getUnitPrice());

        if (result > 0) {
            return giveDiscount(secondItem).multiply(BigDecimal.valueOf(firstItem.getQuantity()));
        }

        return BigDecimal.ZERO;
    }

    // Discount per unit of the first item.
    // If your assumption changes, this is the only line to edit:
    // for "second item's unit price" use: return secondItem.getProduct().getUnitPrice();
    // Discount per unit of the first item: the unit price of the second item.
    private static BigDecimal giveDiscount( CartItem secondItem) {

        return secondItem.getProduct().getUnitPrice();
    }

    private static String format(BigDecimal value) {

        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}