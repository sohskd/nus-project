package com.nus.team3.subscriber;

import com.nus.team3.constants.TradeEnum;
import com.nus.team3.controller.OrderMatchingController;
import com.nus.team3.dao.TransactionDao;
import com.nus.team3.dto.Order;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import com.nus.team3.utils.*;

@Component
public class Subscriber {

	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

	@Autowired
	OrderMatchingController orderMatchingController;

	@Autowired
	@Qualifier("mysqlSqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@SqsListener("order_matching_buyer_queue.fifo")
	public void receiveBuyMessage(String message) {
		logger.info("Message {} Received from order_matching_buyer_queue.fifo", message);
		Order order = constructOrder(message);
		if (order != null){
			sqlSessionTemplate.insert(TransactionDao.rootMapperPath + TransactionDao.saveTxnQuery, order);
			logger.info("Buy order {} successfully persisted to db, proceed to matching.", order.getTransactionId());
			orderMatchingController.processOrder(order);
		}
	}

	@SqsListener("order_matching_seller_queue.fifo")
	public void receiveSellMessage(String message) {
		logger.info("Message {} Received from order_matching_seller_queue.fifo", message);
		Order order = constructOrder(message);
		if (order != null){
			sqlSessionTemplate.insert(TransactionDao.rootMapperPath + TransactionDao.saveTxnQuery, order);
			logger.info("Sell order {} successfully persisted to db, proceed to matching.", order.getTransactionId());
			orderMatchingController.processOrder(order);
		}
	}

	public Order constructOrder(String message){
		try{
			String[] messageBodyList = message.split("#");
			String side = messageBodyList[0];
			String stockName = messageBodyList[1];
			int quantity = Integer.parseInt(messageBodyList[2]);
			float price = Float.parseFloat(String.format("%.2f", Double.valueOf(messageBodyList[3])));
			String user = messageBodyList[4];
			Integer.parseInt(user);
			long timestamp = System.currentTimeMillis();
			String transactionId = Utils.getSaltString();
			if(orderIsValid(side, quantity, price)){
				return new Order(side,transactionId,stockName, user, timestamp,price,quantity);
			}
			else{
				logger.info("Invalid order message received {}, will not processed further", message);
				return null;
			}
		}catch(Exception e){
			logger.info("Invalid order message received {}, will not processed further", message);
			return null;
		}
	}

	private boolean orderIsValid(String side, int quantity, float price){
		return (side.equalsIgnoreCase(TradeEnum.SIDE.BUY.name()) ||
				side.equalsIgnoreCase(TradeEnum.SIDE.SELL.name()))
				&& quantity > 0
				&& price > 0;
	}
}
