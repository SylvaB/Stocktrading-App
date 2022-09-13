package com.kenzie.app;

public class StockHolding {
    // Create private instance properties
    private String companyName;
    private String tickerStockAcronym;
    private double sharePrice;
    private int numShares;

    // Create constructor
    public StockHolding(String companyName,String tickerStockAcronym,double sharePrice){
        this.companyName = companyName;
        this.tickerStockAcronym =tickerStockAcronym;
        this.sharePrice = sharePrice;
        this.numShares = 0;
    }
    // create getters

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTickerStockAcronym() {
        return this.tickerStockAcronym;
    }

    public void setTickerStockAcronym(String tickerStockAcronym) {
        this.tickerStockAcronym = tickerStockAcronym;
    }

    public double getSharePrice() {
        return this.sharePrice;
    }

    public void setSharePrice(double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public int getNumShares() {
        return this.numShares;
    }

    public void setNumOfShares(int numShares) {
        this.numShares = numShares;
    }


    // create buy, sell, and getBalance methods
    public void buy(int numOfShares){
       this.numShares= numOfShares+ this.numShares;

    }

    public double sell(int numOfShares){
            double totalPrice=0;
            if(numOfShares < this.numShares){
                this.numShares =this.numShares- numOfShares;
                totalPrice = this.sharePrice * numOfShares;
                return totalPrice;
            }
           else {
                System.out.println("You don't have enough shares to sell " + numOfShares + "! You only have " + this.numShares);
                return 0;
            }
    }

    public double getBalance(){
        double balance = this.numShares * this.sharePrice;
        return balance;
    }
}
