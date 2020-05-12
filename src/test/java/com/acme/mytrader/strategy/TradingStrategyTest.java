package com.acme.mytrader.strategy;

import com.acme.mytrader.dao.StockMarketDao;
import com.acme.mytrader.dao.StockOwnedDao;
import com.acme.mytrader.service.ExecutionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TradingStrategyTest {

    StockMarketDao stockMarketDao = new StockMarketDao();
    StockOwnedDao stockOwnedDao = new StockOwnedDao();

    ExecutionServiceImpl executionServiceImpl = new ExecutionServiceImpl();

    @Test
    public void buyFirstStockTest() throws Exception {
        Thread.sleep(20000);

        executionServiceImpl.setStockMarketDao(stockMarketDao);
        executionServiceImpl.setStockOwnedDao(stockOwnedDao);

        executionServiceImpl.buy("HP", 0.01, 200);
        assertEquals(1, stockOwnedDao.getStockMarket().size());
    }

    @Test
    public void buySecondStockTest() throws Exception {
        Thread.sleep(20000);

        executionServiceImpl.setStockMarketDao(stockMarketDao);
        executionServiceImpl.setStockOwnedDao(stockOwnedDao);

        executionServiceImpl.buy("IBM", 0.01, 200);
        assertEquals(2, stockOwnedDao.getStockMarket().size());
    }

    @Test
    public void sellFirstStockTest() throws Exception {
        Thread.sleep(20000);

        executionServiceImpl.setStockMarketDao(stockMarketDao);
        executionServiceImpl.setStockOwnedDao(stockOwnedDao);

        executionServiceImpl.sell("HP", 1.00, 200);
        assertTrue(stockMarketDao.getStockMarket()
                .stream()
                .filter(stock -> stock.getSecurity().equalsIgnoreCase("HP"))
                .findFirst().get().getPrice() > 2.00);
    }
}
