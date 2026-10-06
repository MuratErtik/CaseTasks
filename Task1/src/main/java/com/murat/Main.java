package com.murat;


import com.murat.exceptions.LimitMustBeGreaterThanZeroException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {


        System.out.println("**********************************   FindMyChar    **************************************************");
        System.out.println("*****************************************************************************************************");

        Scanner scanner = new Scanner(System.in);

        int limitToInt=-1;

        while(true) {

            System.out.println("Press q to exit the program");

            System.out.print("Before the starting please enter maximum number of characters: ");

            String limit = scanner.next();

            String trimmedLimit = limit.trim();

            if (isProgramQuitting(trimmedLimit)) {
                System.out.println("see you later");
                break;
            }

            try {

                limitToInt = Integer.parseInt(trimmedLimit);


                if (isGreaterThanZero(limitToInt)) {
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

    private static Boolean isGreaterThanZero(int number) {
        return number < 1 ;
    }

    private static Boolean isProgramQuitting(String input) {
        return input.equals("q");
    }
}