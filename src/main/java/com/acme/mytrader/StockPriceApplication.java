package com.acme.mytrader;

import com.acme.mytrader.dao.StockMarketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class StockPriceApplication {

    @Autowired
    private StockMarketDao stockMarketDao;

    public static void main(String[] args) {
        SpringApplication.run(StockPriceApplication.class);
    }

    @PostConstruct
    public void init() {
        stockMarketDao.makePriceVolatile();
    }

}
