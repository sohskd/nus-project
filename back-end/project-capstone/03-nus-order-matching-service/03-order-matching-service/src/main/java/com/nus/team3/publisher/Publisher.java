package com.nus.team3.publisher;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.nus.team3.constants.TradeEnum;
import com.nus.team3.model.MasterPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordermatching")
public class Publisher {

	private static final Logger logger = LoggerFactory.getLogger(Publisher.class);

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Autowired
	private AmazonSQSAsync amazonSQSAsync;

	@Value("${cloud.aws.end-point.buyer.uri}")
	private String buyQueueEndPoint;

	@Value("${cloud.aws.end-point.seller.uri}")
	private String sellQueueEndPoint;

	@Autowired
	private MasterPool masterPool;

	@GetMapping("/testing")
	public String sendTestMessage(){
		return "End point test successful ";
	}

	@PostMapping("/order")
	public SendMessageResult sendOrder(@RequestBody String messageBody) {
		try {
			String[] messageBodyList = messageBody.split("#");
			if (messageBodyList.length != 5 ||
					(!messageBodyList[0].equalsIgnoreCase(TradeEnum.SIDE.BUY.name()) &&
							!messageBodyList[0].equalsIgnoreCase(TradeEnum.SIDE.SELL.name()))) {
				logger.info("Unrecognized order message {}, please make sure message is in format: buy/sell#stockName#quantity#price", messageBody);
				return null;
			}
			String side = messageBodyList[0];
			SendMessageRequest messageRequest = new SendMessageRequest()
					.withQueueUrl(side.equalsIgnoreCase(TradeEnum.SIDE.BUY.name())? buyQueueEndPoint : sellQueueEndPoint)
					.withMessageBody(messageBody);
			messageRequest.setMessageGroupId(side);
			messageRequest.setMessageDeduplicationId(String.valueOf(System.currentTimeMillis()));
			SendMessageResult response = amazonSQSAsync.sendMessage(messageRequest);
			logger.info("Message {} sent successfully to {} queue", messageBody, side);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping("/cancel")
	public String cancelOrder(@RequestBody String messageBody) {
		return masterPool.cancelOrder(messageBody);
	}
}
