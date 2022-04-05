package com.nus.team3.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

	@SqsListener("order_matching_buyer_queue.fifo")
	public void receiveBuyMessage(String stringJson) {
		logger.info("Message {} Received from order_matching_buyer_queue.fifo", stringJson);
	}

	@SqsListener("order_matching_seller_queue.fifo")
	public void receiveSellMessage(String stringJson) {
		logger.info("Message {} Received from order_matching_seller_queue.fifo", stringJson);
	}

}
