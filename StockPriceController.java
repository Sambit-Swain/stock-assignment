package com.acme.mytrader.controller;

import com.acme.mytrader.price.PriceListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockPriceController {

    @Autowired
    private PriceListener priceListener;

    @GetMapping("/finance/addListener")
    public void addListener(@RequestParam(name = "security") String security,
                            @RequestParam(name = "price") double price) {
        priceListener.priceUpdate(security, price);
    }

    @GetMapping("/finance/removeListener")
    public void removeListener(@RequestParam(name = "security") String security) {
        priceListener.priceUpdate(security, -9999.99);
    }
}
