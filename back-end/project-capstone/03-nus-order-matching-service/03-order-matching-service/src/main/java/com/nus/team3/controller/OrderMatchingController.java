package com.nus.team3.controller;

import com.nus.team3.dto.Order;
import com.nus.team3.model.MasterPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMatchingController {

    private static final Logger logger = LoggerFactory.getLogger(OrderMatchingController.class);

    @Autowired
    MasterPool masterPool;

    public void processOrder(Order o){
        masterPool.addOrder(o);
        logger.info("-------------- Before Matching ---------------");
        masterPool.showAllQueue();
        masterPool.match(o);
        logger.info("-------------- After Matching ---------------");
        masterPool.showAllQueue();
    }
}
