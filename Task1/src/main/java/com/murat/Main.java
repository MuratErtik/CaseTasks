package com.murat;


import com.murat.exceptions.ForAnalyzeNeedOneCharException;
import com.murat.exceptions.InputLengthMustBeLessThanOrEqualToLimitException;
import com.murat.exceptions.InputMustNotBeBlankException;
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

        Character charToAnalyze;

        String analyzeAnotherChoice;

        do {

            charToAnalyze = getCharToAnalyze();

            result(input,charToAnalyze,choice);

            analyzeAnotherChoice = getYesNoChoice("Do you want to analyze another character? (y/n): ");

        } while (analyzeAnotherChoice.equals("y"));

        System.out.println("your limit is " + limit + "and your input is " + input+ "and your choice is " + choice+" and your character is " + charToAnalyze);




        System.out.println("**********************************   FindMyChar(Final)  **************************************************");
        System.out.println("*****************************************************************************************************");



    }


    private static int getMaximumNumberOfCharacters() {

        int limitToInt=-1;

        while(true) {

            System.out.print("Before the starting please enter maximum number of characters: ");

            String limit = scanner.nextLine();

            String trimmedLimit = limit.trim();

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

            System.out.print("Please enter your input(only space is not allowed!): ");

            String input = scanner.nextLine();

            String trimmedInput = input.trim();

            try {

                if (trimmedInput.isEmpty()) {
                    throw new InputMustNotBeBlankException("Input must not be blank.");
                }

                if(isInputLengthLessThanOrEqualToLimit(trimmedInput, limit)) {
                    throw new InputLengthMustBeLessThanOrEqualToLimitException("Input length must be less than or equal to the limit.");

                }

                System.out.println(trimmedInput);
                inputToReturn = trimmedInput;

                break;


            }catch (InputMustNotBeBlankException e){
                System.out.println("Input must not be blank. Please try again.");
            }catch (InputLengthMustBeLessThanOrEqualToLimitException e){
                System.out.println("Input length must be less than or equal to the limit. Please try again.");
            }



        }

        return inputToReturn;
    }

    private static String getCaseSensitivityChoice() {

        return getYesNoChoice("Do you want to case sensitivity choice? (y/n): ");
    }

    private static String getYesNoChoice(String prompt) {

        String choiceToReturn = "";

        while(true) {

            System.out.print(prompt);

            String choice = scanner.nextLine();

            String trimmedChoice = choice.trim();

            try {


                if(isInputNotEqualYesOrNo(trimmedChoice)) {
                    throw new InputNotEqualYesOrNoException("Choice must be only Yes(y/Y) or No(n/N).");

                }

                System.out.println(trimmedChoice);
                choiceToReturn = trimmedChoice.toLowerCase().substring(0, 1);

                break;


            }catch (InputNotEqualYesOrNoException e){
                System.out.println("Choice must be only Yes(y/Y) or No(n/N). Please try again.");
            }

        }

        return choiceToReturn;
    }

    private static Character getCharToAnalyze() {

        String inputToReturn = "";

        while(true) {

            System.out.print("Please enter your input to analyze: ");

            String input = scanner.nextLine();

            String trimmedInput = input.trim();



            try {

                if (trimmedInput.length() != 1){
                    throw new ForAnalyzeNeedOneCharException("For analyze it needs to only one character! Program wants to only one character");
                }

                System.out.println(trimmedInput);

                inputToReturn = trimmedInput;

                break;

            }catch (ForAnalyzeNeedOneCharException e){
                System.out.println("For analyze it needs to only one character! Program wants to only one character. Try again!");
            }
        }

        return inputToReturn.charAt(0);

    }

    private static void result(String input,Character charToAnalyze,String choice) {

        if (input == null || choice == null || charToAnalyze == null) {
            System.out.println("Invalid input.");
            return;
        }

        boolean isCaseSensitive = "y".equalsIgnoreCase(choice.trim());

        int count = 0;

        if (isCaseSensitive) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == charToAnalyze) {
                    count++;
                }
            }
        } else {
            char targetLower = Character.toLowerCase(charToAnalyze);
            for (int i = 0; i < input.length(); i++) {
                if (Character.toLowerCase(input.charAt(i)) == targetLower) {
                    count++;
                }
            }
        }

        System.out.println("Target character: '" + charToAnalyze + "'");
        System.out.println("Case sensitive: " + (isCaseSensitive ? "Yes" : "No"));
        System.out.println("count is -->" + count);


    }

    private static Boolean isGreaterThanZero(int number) {
        return number < 1 ;
    }

    private static Boolean isInputLengthLessThanOrEqualToLimit(String input, int limit) {
        return input.length()>limit;
    }

    private static Boolean isInputNotEqualYesOrNo(String input) {

        input = input.toLowerCase();

        return  !(input.equals("no") || input.equals("n") || input.equals("yes") || input.equals("y"));
    }
}