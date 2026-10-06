package com.murat;


import com.murat.domain.Product;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private static final int MAX_NAME_LENGTH = 20;
    private static final BigDecimal MIN_PRICE = new BigDecimal("1");
    private static final BigDecimal MAX_PRICE = new BigDecimal("100");
    private static final BigDecimal MIN_RATING = new BigDecimal("1");
    private static final BigDecimal MAX_RATING = new BigDecimal("5");

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        List<Product> products = createProductsList();

        //debug
        for (Product product : products) {
            System.out.println(product.getName()
                    + " - Price: " + (product.getUnitPrice())
                    + ", Stock: " + product.getStock()
                    + ", Rating: " + product.getRating().toPlainString());
        }




    }

    private static List<Product> createProductsList() {

        int count = productCount();

        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= count; i++) {

            System.out.println("******* Adding Product " + i + ": ******");

            String name = "m";

            BigDecimal price = BigDecimal.valueOf(23);

            int stock = 1;

            BigDecimal rating = BigDecimal.valueOf(2);

            Product product = new Product(name, price, stock, rating);

            products.add(product);

            System.out.println("*************************************************");
        }


        return products;
    }


    private static int productCount() {

        while (true) {

            System.out.print("How many different products do you want to add? ");

            String input = scanner.nextLine().trim();

            try {

                int count = Integer.parseInt(input);
                if (count >= 2) {
                    return count;
                }

                //add exception later maybe?
                System.out.println("You must enter at least 2 different products.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }




}