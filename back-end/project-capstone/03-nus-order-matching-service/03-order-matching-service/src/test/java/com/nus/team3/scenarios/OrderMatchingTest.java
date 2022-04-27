package com.nus.team3.scenarios;

import com.nus.team3.dto.Order;
import com.nus.team3.model.MasterPool;
import com.nus.team3.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderMatchingTest {

    private MasterPool masterPool;

    @BeforeEach
    void setUp() {
        this.masterPool = new MasterPool();
        masterPool.setInit(true);
        System.setProperty("isTestEnv", String.valueOf(true));
    }

    private Order constructOrder(String side, String stock, String quantity, String price, String user){
        return Utils.constructOrder(side + "#" + stock + "#" + quantity + "#" + price + "#" + user);
    }

    @Test
    public void test_whenNoOrderGetMatched(){
        //Although buyOrder 2 and sellOrder 1 price matches, they are not the same stock type. They won't get matched.
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "2", "101.1", "777");
        Order buyOrder_2 = constructOrder("BUY", "APPL", "2", "101.2", "777");
        Order sellOrder_1 = constructOrder("SELL", "MSFT", "1", "101.2", "888");
        masterPool.addOrder(buyOrder_1);
        masterPool.match(buyOrder_1);
        masterPool.addOrder(buyOrder_2);
        masterPool.match(buyOrder_2);
        masterPool.addOrder(sellOrder_1);
        masterPool.match(sellOrder_1);
        assertEquals(1, masterPool.getStockMap().get("MSFT").getBuyQueue().size());
        assertEquals(1, masterPool.getStockMap().get("MSFT").getSellQueue().size());
        assertEquals(1, masterPool.getStockMap().get("APPL").getBuyQueue().size());
    }

    @Test
    public void test_whenOrderGetFullyMatched(){
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "2", "101.2", "777");
        Order sellOrder_1 = constructOrder("SELL", "MSFT", "2", "101.1", "777");
        masterPool.addOrder(buyOrder_1);
        masterPool.match(buyOrder_1);
        masterPool.addOrder(sellOrder_1);
        masterPool.match(sellOrder_1);
        assertEquals(0, masterPool.getStockMap().get("MSFT").getBuyQueue().size());
        assertEquals(0, masterPool.getStockMap().get("MSFT").getSellQueue().size());
    }

    @Test
    public void test_whenOrderGetPartiallyMatched(){
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "2", "101.2", "777");
        Order sellOrder_1 = constructOrder("SELL", "MSFT", "1", "101.1", "777");
        masterPool.addOrder(buyOrder_1);
        masterPool.match(buyOrder_1);
        masterPool.addOrder(sellOrder_1);
        masterPool.match(sellOrder_1);
        assertEquals(1, masterPool.getStockMap().get("MSFT").getBuyQueue().size());
        assertEquals(0, masterPool.getStockMap().get("MSFT").getSellQueue().size());
    }

    @Test
    public void test_whenOrderGetFullyMatched_OneToMany(){
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "2", "101.2", "777");
        Order sellOrder_1 = constructOrder("SELL", "MSFT", "1", "101.1", "777");
        Order sellOrder_2 = constructOrder("SELL", "MSFT", "1", "101.1", "888");
        masterPool.addOrder(buyOrder_1);
        masterPool.match(buyOrder_1);
        masterPool.addOrder(sellOrder_1);
        masterPool.match(sellOrder_1);
        masterPool.addOrder(sellOrder_2);
        masterPool.match(sellOrder_2);
        assertEquals(0, masterPool.getStockMap().get("MSFT").getBuyQueue().size());
        assertEquals(0, masterPool.getStockMap().get("MSFT").getSellQueue().size());
    }

    @Disabled
    @Test
    public void test_whenOrderGetFullyMatched_FirstComeFirstServe(){
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "1", "101.2", "777");
        Order buyOrder_2 = constructOrder("BUY", "MSFT", "1", "101.2", "888");
        Order sellOrder_1 = constructOrder("SELL", "MSFT", "1", "101.1", "999");
        masterPool.addOrder(buyOrder_1);
        masterPool.match(buyOrder_1);
        masterPool.addOrder(buyOrder_2);
        masterPool.match(buyOrder_2);
        masterPool.addOrder(sellOrder_1);
        masterPool.match(sellOrder_1);
        assertEquals(1, masterPool.getStockMap().get("MSFT").getBuyQueue().size());
        assertEquals(0, masterPool.getStockMap().get("MSFT").getSellQueue().size());
        // 2nd buyOrder left out in the queue as it comes late
        assertEquals(888, masterPool.getStockMap().get("MSFT").getBuyQueue().firstKey().getUser());
    }
}
