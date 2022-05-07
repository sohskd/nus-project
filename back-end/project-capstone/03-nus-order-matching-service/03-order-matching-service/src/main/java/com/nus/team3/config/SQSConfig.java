package com.nus.team3.config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {
	// @Value("${cloud.aws.region.static}")
	private String region;

	// @Value("${cloud.aws.credentials.access-key}")
	private String accessKey;

	// @Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;

	// @Bean
	// public QueueMessagingTemplate queueMessagingTemplate() {
	// 	return new QueueMessagingTemplate(amazonSQSAsync());
	// }

	@Bean
	public AmazonSQSAsync amazonSQSAsync() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("x", "x");
		EndpointConfiguration endpointConfiguration = new AmazonSQSAsyncClientBuilder.EndpointConfiguration(
				"http://localhost:9324",
				"ap-southeast-2");
		AmazonSQSAsyncClientBuilder amazonSQSAsyncClientBuilder = AmazonSQSAsyncClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
				.withEndpointConfiguration(endpointConfiguration);
		AmazonSQSAsync amazonSQSAsync = amazonSQSAsyncClientBuilder.build();
		return amazonSQSAsync;
	}

}
