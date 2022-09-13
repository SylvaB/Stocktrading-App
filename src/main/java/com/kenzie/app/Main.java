package com.kenzie.app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
        System.out.println("Welcome to the Kenzie Stock Exchange");
        Scanner in = new Scanner(System.in);
        System.out.println("What is the Stock's name?");
        String name = in.nextLine();
        System.out.println("What is the Stock's ticker symbol?");
        String ticker = in.nextLine();
        System.out.println("What is the Stock's current price?");
        double price = Double.parseDouble(in.nextLine());

        // Create your StockHolding using the values above
        StockHolding stockHolding = new StockHolding(name, ticker, price);
        int selectedNumber = 0;
        int numOfShares = 0;
        double totalSharesPrice = 0;
        String illegalArgumentException = "You need to enter a number between 1 and 4!";

            while (selectedNumber<4) {
                try {
                switch (selectedNumber) {
                    case 1:
                        System.out.println("1. Check your Balance");
                        System.out.println("You own " + stockHolding.getNumShares() + " shares of " + stockHolding.getCompanyName() + " (" + stockHolding.getTickerStockAcronym() + ");" + "\n" +
                                "Your balance is: $" + stockHolding.getBalance());
                        break;
                    case 2:
                        System.out.println("2. Buy");
                        System.out.println("How many shares would you like to buy?");
                        numOfShares = in.nextInt();
                        in.nextLine();
                        stockHolding.buy(numOfShares);
                        totalSharesPrice = stockHolding.getSharePrice() * numOfShares;
                        System.out.println("You bought " + numOfShares + " shares for $" + totalSharesPrice);
                        break;

                    case 3:
                        System.out.println("3. Sell");
                        System.out.println("How many shares would you like to sell?");
                        numOfShares = in.nextInt();
                        in.nextLine();
                        System.out.println("You sold " + numOfShares + " shares for $" + stockHolding.sell(numOfShares));
                        break;

                    case 4:
                        System.out.println("4. Exit");
                        break;
                }
                System.out.println("Enter a selection (1-4)" + "\n" + "1. Check your Balance" + "\n" + "2. Buy" + "\n" + "3. Sell" + "\n" + "4. Exit");
                selectedNumber = in.nextInt();
                in.nextLine();
                    if (selectedNumber > 4) {
                        throw new InvalidUserInputException(illegalArgumentException);
                    }
                } catch (InvalidUserInputException e) {
                    selectedNumber = 0;
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (Exception e){
            System.out.println("Unexpected error: " + e);
        }
        System.out.println("Goodbye!");
    }
}

