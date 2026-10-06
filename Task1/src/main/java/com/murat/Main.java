package com.murat;


import com.murat.exceptions.LimitMustBeGreaterThanZeroException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {


        System.out.println("**********************************   FindMyChar    **************************************************");
        System.out.println("*****************************************************************************************************");

        Scanner scanner = new Scanner(System.in);

        int limitToInt;

        while(true) {

            System.out.print("Before the starting please enter maximum number of characters: ");

            String limit = scanner.next();

            String trimmedLimit = limit.trim();

            try {

                limitToInt = Integer.parseInt(trimmedLimit);

                if (limitToInt < 1) {
                    throw new LimitMustBeGreaterThanZeroException("Limit must be greater than 0");
                }

                break;

            }catch (NumberFormatException e){
                System.out.println("Please enter a valid integer.");
            }catch (LimitMustBeGreaterThanZeroException e){
                System.out.println("Please enter a positive number for the maximum number of characters.");
            }

        }

        System.out.println("your limit is " + limitToInt);



        System.out.println("**********************************   FindMyChar(Final)  **************************************************");
        System.out.println("*****************************************************************************************************");



    }
}