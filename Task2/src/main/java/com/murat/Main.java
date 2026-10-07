package com.murat;


import com.murat.domain.Cart;
import com.murat.domain.DiscountCalculator;
import com.murat.domain.Product;

import java.math.BigDecimal;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
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

        boolean ascending = sortOrderBy();

        System.out.println(ascending);

        List<Product> sortedProducts = sortProducts(products, criteria, ascending);

        printProducts(sortedProducts);

        Cart cart = fillCart(products);








    }

    private static Cart fillCart(List<Product> products) {

        Cart cart = new Cart();

        if (!getYesNo("Do you want to add products to your cart? (Yes/No): ")) {
            return cart;
        }

        while (true) {

            addProductToCart(cart, products);

            // recalculates from the whole cart and prints the discount info
            DiscountCalculator.comparativeDiscount(cart);

            if (getYesNo("Do you want to add another product? (Yes/No): ")) {
                continue;
            }

            if (cart.getItems().size() >= 2) {
                break;
            }

            System.out.println("You must add at least 2 different products.");
        }

        return cart;
    }



    private static boolean getYesNo(String input) {

        while (true) {

            System.out.print(input);

            String trimmedInput = scanner.nextLine().trim().toLowerCase();

            if (trimmedInput.equals("yes")) {
                return true;
            }
            if (trimmedInput.equals("no")) {
                return false;
            }

            System.out.println("Please enter a valid answer.");
        }
    }




    private static List<Product> sortProducts(List<Product> products, String criteria, boolean ascending) {

        Comparator<Product> byName = Comparator.comparing(p -> p.getName().toLowerCase());

        Comparator<Product> comparator;

        if (criteria.equals("stock")) {

            comparator = Comparator.comparingInt(Product::getStock);

        } else if (criteria.equals("rating")) {

            comparator = Comparator.comparingDouble(Product::getRating);

        } else {
            comparator = byName;
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        //if other units are same with each other after than compare with this field!!
        if (!criteria.equals("name")) {
            comparator = comparator.thenComparing(byName);
        }

        List<Product> sorted = new ArrayList<>(products);


        sorted.sort(comparator);

        return sorted;
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

    private static boolean sortOrderBy() {

        while (true) {

            System.out.print("Ascending or descending? (ascending/descending): ");

            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("ascending")) {
                return true;
            }
            if (input.equals("descending")) {
                return false;
            }

            System.out.println("Please enter a valid order: ascending or descending.");
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


    private static void printProducts(List<Product> products) {

        System.out.println("Sorted Products:");

        for (Product product : products) {
            System.out.println("*****************************************");
            System.out.println(product.getName()
                    + " - Price: " + product.getUnitPrice().setScale(2, RoundingMode.HALF_UP).toString()
                    + ", Stock: " + product.getStock()
                    + ", Rating: " + product.getRating());
            System.out.println("*****************************************");
        }
    }


}