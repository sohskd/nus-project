package com.nus.team3.subscriber;

import com.nus.team3.constants.TradeEnum;
import com.nus.team3.controller.OrderMatchingController;
import com.nus.team3.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Subscriber {

	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

	@Autowired
	OrderMatchingController orderMatchingController;

	@SqsListener("order_matching_buyer_queue.fifo")
	public void receiveBuyMessage(String message) {
		logger.info("Message {} Received from order_matching_buyer_queue.fifo", message);
		Order order = constructOrder(message);
		if (order != null){orderMatchingController.addOrder(order);}
	}

	@SqsListener("order_matching_seller_queue.fifo")
	public void receiveSellMessage(String message) {
		logger.info("Message {} Received from order_matching_seller_queue.fifo", message);
		Order order = constructOrder(message);
		if (order != null){orderMatchingController.addOrder(order);}
	}

	public Order constructOrder(String message){
		try{
			String[] messageBodyList = message.split("#");
			String side = messageBodyList[0];
			String stockName = messageBodyList[1];
			int quantity = Integer.parseInt(messageBodyList[2]);
			float price = Float.parseFloat(messageBodyList[3]);
			String user = messageBodyList[4];
			long timestamp = System.currentTimeMillis();
			String transactionId = getSaltString();
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

	private String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	private boolean orderIsValid(String side, int quantity, float price){
		return (side.equalsIgnoreCase(TradeEnum.SIDE.BUY.name()) ||
				side.equalsIgnoreCase(TradeEnum.SIDE.SELL.name()))
				&& quantity > 0
				&& price > 0;
	}
}
