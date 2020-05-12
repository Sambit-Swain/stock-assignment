package com.acme.mytrader.dao;

import com.acme.mytrader.model.Stock;
import org.springframework.stereotype.Repository;

import java.text.DecimalFormat;
import java.util.*;

@Repository
public class StockMarketDao {

    private static List<Stock> stock = new ArrayList<>();

    static {
        stock.add(new Stock("IBM", 234.11, 1000));
        stock.add(new Stock("HP", 845.98, 1000));
        stock.add(new Stock("DELL", 984.55, 1000));
    }

    public void makePriceVolatile() {
        Timer timer = new Timer();
        DecimalFormat format = new DecimalFormat("##.##");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                double price = random.nextDouble() * 200;
                int index = random.nextInt(3);
                stock.get(index).setPrice(Double.parseDouble(format.format(price)));
            }
        }, 0, 30000);
    }

    public List<Stock> getStockMarket() {
        return stock;
    }

}
