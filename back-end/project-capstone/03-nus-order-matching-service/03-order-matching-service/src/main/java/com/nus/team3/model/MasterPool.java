package com.nus.team3.model;

import com.nus.team3.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MasterPool {

    private Map<String, StockOrderPool> stockMap = new HashMap<>();

    @Autowired
    public MasterPool() {
    }

    public void addOrder(Order o){
        if (!stockMap.keySet().contains(o.getStockTicker())){
            stockMap.put(o.getStockTicker(), new StockOrderPool(o.getStockTicker()));
        }
        stockMap.get(o.getStockTicker()).addByPriceByTimestamp(o);
    }

    public void match(Order o){
        stockMap.get(o.getStockTicker()).match();
    }

    public void showAllQueue(){
        for (StockOrderPool stockOrderPool: stockMap.values()){
            stockOrderPool.print();
        }
    }

}
