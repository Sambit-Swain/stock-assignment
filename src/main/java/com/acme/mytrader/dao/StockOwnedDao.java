package com.acme.mytrader.dao;

import com.acme.mytrader.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StockOwnedDao {

    private List<Stock> stock = new ArrayList<>();

    public List<Stock> getStockMarket() {
        return this.stock;
    }

}
