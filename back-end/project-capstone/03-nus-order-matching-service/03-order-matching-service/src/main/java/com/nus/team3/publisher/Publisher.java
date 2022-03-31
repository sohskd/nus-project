package com.nus.team3.publisher;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
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

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;

	@GetMapping("/testing")
	public String sendTestMessage(){
		return "End point test successful ";
	}

	@PostMapping("/message")
	public SendMessageResult sendMessage(@RequestBody String messageBody) {

		try {
			SendMessageRequest messageRequest = new SendMessageRequest()
					.withQueueUrl(endPoint)
					.withMessageBody(messageBody);
			messageRequest.setMessageGroupId("1");
			messageRequest.setMessageDeduplicationId(String.valueOf(System.currentTimeMillis()));
			SendMessageResult response = amazonSQSAsync.sendMessage(messageRequest);
			logger.info("Message sent successfully  " + messageBody);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
