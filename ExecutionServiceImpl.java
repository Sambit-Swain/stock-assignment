package com.acme.mytrader.service;

import com.acme.mytrader.dao.StockMarketDao;
import com.acme.mytrader.dao.StockOwnedDao;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Autowired
    private StockMarketDao stockMarketDao;

    @Autowired
    private StockOwnedDao stockOwnedDao;

    @Override
    public void buy(String security, double price, int volume) {

        if (stockMarketDao.getStockMarket().stream().anyMatch(x -> x.getSecurity().equalsIgnoreCase(security))) {
            Stock oldMarketStock = stockMarketDao.getStockMarket()
                    .stream()
                    .filter(stockOM -> stockOM.getSecurity().equalsIgnoreCase(security))
                    .findFirst().get();

            Stock oldOwnedStock = null;

            if (stockOwnedDao.getStockMarket().isEmpty()) {
                stockOwnedDao.getStockMarket().add(new Stock(security, price, volume));
                stockMarketDao.getStockMarket()
                        .stream()
                        .filter(stockM -> stockM.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setVolume(oldMarketStock.getVolume() - volume);

            } else if (stockOwnedDao.getStockMarket().stream().noneMatch(stockO -> stockO.getSecurity().equalsIgnoreCase(security))) {
                stockOwnedDao.getStockMarket().add(new Stock(security, price, volume));
                stockMarketDao.getStockMarket()
                        .stream()
                        .filter(stockM -> stockM.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setVolume(oldMarketStock.getVolume() - volume);
            } else {
                oldOwnedStock = stockOwnedDao.getStockMarket()
                        .stream()
                        .filter(stockOO -> stockOO.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get();

                stockOwnedDao.getStockMarket()
                        .stream()
                        .filter(stockO -> stockO.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setVolume(oldOwnedStock.getVolume() + volume);

                stockOwnedDao.getStockMarket()
                        .stream()
                        .filter(stockO -> stockO.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setPrice(price);

                stockMarketDao.getStockMarket()
                        .stream()
                        .filter(stockM -> stockM.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setVolume(oldMarketStock.getVolume() - volume);
            }

            displayUpdate();
        }
    }

    @Override
    public void sell(String security, double price, int volume) {
        Stock oldMarketStock = null;
        if (stockMarketDao.getStockMarket().stream().anyMatch(stockOM -> stockOM.getSecurity().equalsIgnoreCase(security)))
            oldMarketStock = stockMarketDao.getStockMarket()
                    .stream()
                    .filter(stockOM -> stockOM.getSecurity().equalsIgnoreCase(security))
                    .findFirst()
                    .get();

        if (!stockOwnedDao.getStockMarket().isEmpty()) {
            Stock oldOwnedStock = stockOwnedDao.getStockMarket()
                    .stream()
                    .filter(stockOO -> stockOO.getSecurity().equalsIgnoreCase(security))
                    .findFirst()
                    .get();

            if (oldOwnedStock.getVolume() >= 20) {
                stockOwnedDao.getStockMarket()
                        .stream()
                        .filter(stockO -> stockO.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setVolume(oldOwnedStock.getVolume() - volume);

                stockOwnedDao.getStockMarket()
                        .stream()
                        .filter(stockO -> stockO.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setPrice(price);

                stockMarketDao.getStockMarket()
                        .stream()
                        .filter(stockM -> stockM.getSecurity().equalsIgnoreCase(security))
                        .findFirst()
                        .get()
                        .setVolume(oldMarketStock.getVolume() + volume);

                displayUpdate();
            }
        }
    }

    public void displayUpdate() {
        stockMarketDao.getStockMarket()
                .forEach(stockM -> System.out.print("(" + stockM.getSecurity() + ") " + "£" + stockM.getPrice() + ": [" + stockM.getVolume() + "Unit] | "));
        if (!stockOwnedDao.getStockMarket().isEmpty()) {
            stockOwnedDao.getStockMarket()
                    .forEach(stockO -> System.out.print("(" + stockO.getSecurity() + ") " + "£" + stockO.getPrice() + ": [" + stockO.getVolume() + "Unit] | "));
            System.out.println("");
        }
    }

    public void setStockMarketDao(StockMarketDao stockMarketDao) {
        this.stockMarketDao = stockMarketDao;
    }

    public void setStockOwnedDao(StockOwnedDao stockOwnedDao) {
        this.stockOwnedDao = stockOwnedDao;
    }
}
