package com.murat.domain;

import java.math.BigDecimal;

public class Product {

    private final String name;

    private final BigDecimal unitPrice;

    private final int stock;

    private final BigDecimal rating;


    public Product(String name, BigDecimal unitPrice, int stock, BigDecimal rating) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getRating() {
        return rating;
    }

}
