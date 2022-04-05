package com.nus.team3.controller;

import com.nus.team3.dto.Order;
import com.nus.team3.model.MatchingPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMatchingController {

    @Autowired
    MatchingPool matchingPool;

    public void addOrder(Order o){
        matchingPool.addByPriceByTimestamp(o);
        matchingPool.print();
    }
}
