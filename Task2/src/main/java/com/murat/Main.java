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
    private static final Double MIN_RATING = Double.valueOf("1.00");
    private static final Double MAX_RATING =  Double.valueOf("5.00");

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        List<Product> products = createProductsList();

        String criteria = getSortCriteria();

        System.out.println(criteria);




    }

    private static String getSortCriteria() {

        while (true) {

            System.out.print("Which criterion do you want to sort products? (name/stock/rating): ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("name") || input.equals("stock") || input.equals("rating")) {
                return input;
            }

            System.out.println("Please enter a valid criteria: name, stock or rating.");
        }
    }

    private static List<Product> createProductsList() {

        int count = productCount();

        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= count; i++) {

            System.out.println("******* Adding Product " + i + ": ******");

            String name = setProductName(products);

            BigDecimal price = setProductPrice();

            int stock = setProductStock();

            Double rating =setProductRating();

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

    private static int setProductStock() {

        while (true) {

            System.out.print("Stock quantity: ");

            String input = scanner.nextLine().trim();

            try {

                int stock = Integer.parseInt(input);

                if (stock >= 1) {

                    return stock;
                }

                System.out.println("Stock must be at least 1.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static Double setProductRating() {

        while (true) {

            System.out.print("Rating: ");

            Double rating = null;

            try {
                rating = scanner.nextDouble();

            } catch (java.util.InputMismatchException e) {

                scanner.nextLine();
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (rating == null) {

                System.out.println("Please enter a valid rating with at most 2 decimal places.");

            } else if (rating.compareTo(MIN_RATING) < 0 || rating.compareTo(MAX_RATING) > 0) {
                System.out.println("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
            } else {

                return rating;
            }
        }
    }


}