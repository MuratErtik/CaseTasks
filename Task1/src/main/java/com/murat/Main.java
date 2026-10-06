package com.murat;


import com.murat.exceptions.InputLengthMustBeLessThanOrEqualToLimitException;
import com.murat.exceptions.InputNotEqualYesOrNoException;
import com.murat.exceptions.LimitMustBeGreaterThanZeroException;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {


        System.out.println("**********************************   FindMyChar    **************************************************");
        System.out.println("*****************************************************************************************************");


        int limit = getMaximumNumberOfCharacters();

        String input = getInput(limit);

        String choice = getCaseSensitivityChoice();

        System.out.println("your limit is " + limit + "and your input is " + input+ "and your choice is " + choice);





        System.out.println("**********************************   FindMyChar(Final)  **************************************************");
        System.out.println("*****************************************************************************************************");



    }


    private static int getMaximumNumberOfCharacters() {

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

        return limitToInt;
    }

    private static String getInput(int limit) {

        String inputToReturn = "";

        while(true) {

            System.out.println("Press q to exit the program");

            System.out.print("Please enter your input(only space is not allowed!): ");

            String input = scanner.next();

            String trimmedInput = input.trim();

            if (isProgramQuitting(trimmedInput)) {
                System.out.println("see you later");
                break;
            }

            try {


                if(isInputLengthLessThanOrEqualToLimit(trimmedInput, limit)) {
                    throw new InputLengthMustBeLessThanOrEqualToLimitException("Input length must be less than or equal to the limit.");

                }

                System.out.println(trimmedInput);
                inputToReturn = trimmedInput;

                break;


            }catch (InputLengthMustBeLessThanOrEqualToLimitException e){
                System.out.println("Input length must be less than or equal to the limit. Please try again.");
            }



        }

        return inputToReturn;
    }

    private static String getCaseSensitivityChoice() {

        String choiceToReturn = "";

        while(true) {

            System.out.print("Do you want to case sensitivity choice? (y/n): ");

            String choice = scanner.next();

            String trimmedChoice = choice.trim().substring(0, 1);

            try {


                if(isInputNotEqualYesOrNo(trimmedChoice)) {
                    throw new InputNotEqualYesOrNoException("Choice must be only Yes(y/Y) or No(n/N).");

                }

                System.out.println(trimmedChoice);
                choiceToReturn = trimmedChoice.toLowerCase();

                break;


            }catch (InputNotEqualYesOrNoException e){
                System.out.println("Choice must be only Yes(y/Y) or No(n/N). Please try again.");
            }

        }

        return choiceToReturn;
    }

    private static Boolean isGreaterThanZero(int number) {
        return number < 1 ;
    }

    private static Boolean isProgramQuitting(String input) {
        return input.equals("q");
    }

    private static Boolean isInputLengthLessThanOrEqualToLimit(String input, int limit) {
        return input.length()>limit;
    }

    private static Boolean isInputNotEqualYesOrNo(String input) {

        input = input.toLowerCase();

        return  !(input.equals("no") || input.equals("n") || input.equals("yes") || input.equals("y"));
    }
}