package com.nus.team3.scenarios;

import com.nus.team3.dto.Order;
import com.nus.team3.model.MasterPool;
import com.nus.team3.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCancellationTest {

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
    public void test_whenOrderGetCancelled(){
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "2", "101.1", "777");
        masterPool.addOrder(buyOrder_1);
        String response = masterPool.cancelOrder(buyOrder_1.getTransactionId());
        assertEquals("Success", response);
        assertEquals(0, masterPool.getStockMap().get("MSFT").getBuyQueue().size());
    }

    @Test
    public void test_whenMatchedOrderCannotGetCancelled(){
        Order buyOrder_1 = constructOrder("BUY", "MSFT", "1", "101.2", "777");
        Order sellOrder_1 = constructOrder("SELL", "MSFT", "1", "101.1", "888");
        masterPool.addOrder(buyOrder_1);
        masterPool.match(buyOrder_1);
        masterPool.addOrder(sellOrder_1);
        masterPool.match(sellOrder_1);
        String response = masterPool.cancelOrder(buyOrder_1.getTransactionId());
        assertEquals(String.format("TransactionId %s not found in queue.", buyOrder_1.getTransactionId()), response);
    }
}
