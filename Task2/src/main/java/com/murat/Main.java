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

            String name = setProductName(products);

            BigDecimal price = setProductPrice();

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



    private static String setProductName(List<Product> existing) {

        while (true) {

            System.out.print("Product name: ");

            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Product name cannot be empty.");
            } else if (name.length() > MAX_NAME_LENGTH) {
                System.out.println("Product name cannot be longer than " + MAX_NAME_LENGTH + " characters.");
            } else if (nameAlreadyExistsInProducts(existing, name)) {
                System.out.println("A product with this name already exists.");
            } else {

                return name;
            }
        }
    }

    private static boolean nameAlreadyExistsInProducts(List<Product> products, String name) {

        for (Product product : products) {

            if (product.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    private static BigDecimal setProductPrice() {

        while (true) {

            System.out.print("Unit price: ");

            String input = scanner.nextLine().trim();

            BigDecimal price;

            try {
                price = new BigDecimal(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }


            if (price == null) {
                System.out.println("Please enter a valid price with at most 2 decimal places.");
            } else if (price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
                System.out.println("Price must be between " + MIN_PRICE +" and " + MAX_PRICE);
            } else {
                return price;
            }
        }
    }


}