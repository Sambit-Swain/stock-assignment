package com.acme.mytrader.service;

import com.acme.mytrader.dao.StockMarketDao;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class PriceSourceImpl implements PriceSource {

    @Autowired
    private StockMarketDao stockMarketDao;

    @Autowired
    private ExecutionService executionService;

    private Timer timer = new Timer();

    @Override
    public void addPriceListener(PriceListener listener) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                double volatileStockPrice = stockMarketDao.getStockMarket()
                        .stream()
                        .filter(stock -> stock.getSecurity().equalsIgnoreCase(listener.getStockSecurity()))
                        .findFirst().get().getPrice();

                if (volatileStockPrice < listener.getStockPrice())
                    executionService.buy(listener.getStockSecurity(), volatileStockPrice, 10);

                if (volatileStockPrice > (listener.getStockPrice() * 2.0))
                    executionService.sell(listener.getStockSecurity(), volatileStockPrice, 20);
            }
        }, 0, 30000);
    }

    @Override
    public void removePriceListener(PriceListener listener) {
        timer.cancel();
    }
}
