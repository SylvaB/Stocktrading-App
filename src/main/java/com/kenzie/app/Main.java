package com.kenzie.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Kenzie Stock Exchange");
        Scanner in = new Scanner(System.in);
        System.out.println("What is the Stock's name?");
        String name = in.nextLine();
        System.out.println("What is the Stock's ticker symbol?");
        String ticker = in.nextLine();
        System.out.println("What is the Stock's current price?");
        double price = Double.parseDouble(in.nextLine());

        // Create your StockHolding using the values above

        // Create your Execution Loop
            // print the menu
            // collect which option they chose from Scanner
            // perform the operation: Check your balance, buy, sell, or exit
            // repeat!

        System.out.println("Goodbye!");
    }
}
