package com.murat;


import com.murat.exceptions.LimitMustBeGreaterThanZeroException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {


        System.out.println("**********************************   FindMyChar    **************************************************");
        System.out.println("*****************************************************************************************************");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Before the starting please enter maximum number of characters: ");

        String limit = scanner.next();

        String trimmedLimit = limit.trim();

        System.out.println(trimmedLimit);

    }
}