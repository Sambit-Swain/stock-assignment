package com.acme.mytrader.model;

public class Stock {

    private String security;
    private double price;
    private int volume;

    public Stock(String security, double price, int volume) {
        this.security = security;
        this.price = price;
        this.volume = volume;
    }

    public String getSecurity() {
        return security;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
