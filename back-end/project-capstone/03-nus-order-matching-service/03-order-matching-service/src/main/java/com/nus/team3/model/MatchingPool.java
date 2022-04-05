package com.nus.team3.model;

import com.nus.team3.constants.TradeEnum;
import com.nus.team3.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component
public class MatchingPool {

    private TreeMap<Order,String> buyQueue = new TreeMap<Order, String>();
    private TreeMap<Order,String> sellQueue = new TreeMap<Order, String>();

    @Autowired
    public MatchingPool() {
    }

    public void addByPriceByTimestamp(Order order){
        if (order.getBuyOrSell().equalsIgnoreCase(TradeEnum.SIDE.BUY.name())){
            buyQueue.put(order,order.getTransactionId());
        }else{
            sellQueue.put(order,order.getTransactionId());
        }
    }

    public void print(){
        System.out.print("B:");
        for (Order order:buyQueue.keySet()){
            System.out.print(" ");
            order.print();
        }
        System.out.println();
        System.out.print("S:");
        for (Order order:sellQueue.keySet()){
            System.out.print(" ");
            order.print();
        }
        System.out.println();
    }

}
