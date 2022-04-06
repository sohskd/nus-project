package com.nus.team3.model;

import com.nus.team3.constants.TradeEnum;
import com.nus.team3.dto.Order;
import com.nus.team3.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class StockOrderPool {

    private TreeMap<Order,String> buyQueue = new TreeMap<Order, String>();
    private TreeMap<Order,String> sellQueue = new TreeMap<Order, String>();
    private List<Order> matchedOrderQueue = new ArrayList<>();
    private String stockName;

    public StockOrderPool(String stockName) {
        this.stockName = stockName;
    }

    public void addByPriceByTimestamp(Order order){
        if (order.getBuyOrSell().equalsIgnoreCase(TradeEnum.SIDE.BUY.name())){
            buyQueue.put(order,order.getTransactionId());
        }else{
            sellQueue.put(order,order.getTransactionId());
        }
    }

    public void match(){
        if (buyQueue.size() > 0 && sellQueue.size() > 0) {
            Order bestBuyOrder = buyQueue.firstEntry().getKey();
            Order bestSellOrder = sellQueue.firstEntry().getKey();
            if (bestBuyOrder.getPrice() >= bestSellOrder.getPrice()) {
                processMatchedOrder(bestBuyOrder, bestSellOrder);
                buyQueue.remove(bestBuyOrder);
                sellQueue.remove(bestSellOrder);
                match();
            }
        }
    }

    public void processMatchedOrder(Order buyOrder, Order sellOrder){
        String transactionIdAfterMatch = Utils.getSaltString();

        buyOrder.setTransactionIdAfterMatch(transactionIdAfterMatch);
        buyOrder.setMatchStatus(TradeEnum.STATUS.MATCHED.name());

        sellOrder.setTransactionIdAfterMatch(transactionIdAfterMatch);
        sellOrder.setMatchStatus(TradeEnum.STATUS.MATCHED.name());
        sellOrder.setPrice(buyOrder.getPrice());

        matchedOrderQueue.add(buyOrder);
        matchedOrderQueue.add(sellOrder);
    }

    public void print(){
        System.out.print(this.stockName + " B:");
        for (Order order:buyQueue.keySet()){
            System.out.print(" ");
            order.print();
        }
        System.out.println();
        System.out.print(this.stockName + " S:");
        for (Order order:sellQueue.keySet()){
            System.out.print(" ");
            order.print();
        }
        System.out.println();
        System.out.print(this.stockName + " M:");
        for (Order order:matchedOrderQueue){
            System.out.print(" ");
            order.print();
        }
        System.out.println();
    }
}
