package com.nus.team3.model;

import com.nus.team3.constants.TradeEnum;
import com.nus.team3.dto.Order;
import com.nus.team3.http.HttpSender;
import com.nus.team3.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class StockOrderPool {

    private static final Logger logger = LoggerFactory.getLogger(StockOrderPool.class);

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
                int buyQty = bestBuyOrder.getQuantity();
                int sellQty = bestSellOrder.getQuantity();
                int excessBuyQty = buyQty - Math.min(buyQty, sellQty);
                int excessSellQty = sellQty - Math.min(buyQty, sellQty);
                Order excessBuyOrder = null;
                Order excessSellOrder = null;
                if (excessBuyQty > 0) {
                    bestBuyOrder.setQuantity(Math.min(buyQty, sellQty));
                    logger.info("Creating new excess order");
                    excessBuyOrder = new Order(bestBuyOrder.getBuyOrSell(), bestBuyOrder.getTransactionId(), bestBuyOrder.getStockTicker(), String.valueOf(bestBuyOrder.getUser()), bestBuyOrder.getTimestamp(), bestBuyOrder.getPrice(),
                            excessBuyQty);
                    new HttpSender().sendOrder(excessBuyOrder);
                } else if (excessSellQty > 0) {
                    bestSellOrder.setQuantity(Math.min(buyQty, sellQty));
                    excessSellOrder = new Order(bestSellOrder.getBuyOrSell(), bestSellOrder.getTransactionId(), bestSellOrder.getStockTicker(), String.valueOf(bestSellOrder.getUser()), bestSellOrder.getTimestamp(), bestSellOrder.getPrice(),
                            excessSellQty);
                    new HttpSender().sendOrder(excessSellOrder);
                }
                processMatchedOrder(bestBuyOrder, bestSellOrder);
                buyQueue.remove(bestBuyOrder);
                sellQueue.remove(bestSellOrder);
                if(excessBuyOrder != null ){buyQueue.put(excessBuyOrder,excessBuyOrder.getTransactionId());}
                if(excessSellOrder != null ){sellQueue.put(excessSellOrder,excessSellOrder.getTransactionId());}
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

        new HttpSender().sendOrder(buyOrder);
        logger.info("Matched order {} successfully persisted to db, id after match is {}",
                buyOrder.getTransactionId(),
                buyOrder.getTransactionIdAfterMatch());

        new HttpSender().sendOrder(sellOrder);
        logger.info("Matched order {} successfully persisted to db, id after match is {}",
                sellOrder.getTransactionId(),
                sellOrder.getTransactionIdAfterMatch());
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
