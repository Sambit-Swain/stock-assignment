package com.acme.mytrader.service;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListenerImpl implements PriceListener {

    @Autowired
    private PriceSource priceSource;

    private String security = "";
    private double price = 0.00;

    private PriceListenerImpl priceListenerImpl;

    @Override
    public void priceUpdate(String security, double price) {

        if (priceListenerImpl == null)
            priceListenerImpl = new PriceListenerImpl();

        priceListenerImpl.setSecurity(security);
        priceListenerImpl.setPrice(price);

        if (price != -9999.99) {
            priceSource.addPriceListener(priceListenerImpl);
        } else {
            priceSource.removePriceListener(priceListenerImpl);
        }
    }

    private void setSecurity(String security) {
        this.security = security;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getStockSecurity() {
        return this.security;
    }

    @Override
    public double getStockPrice() {
        return this.price;
    }
}
