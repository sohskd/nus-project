package com.nus.team3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { ContextStackAutoConfiguration.class })
@ComponentScan(basePackages = "com.nus.team3.*")
public class OrderMatchingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderMatchingServiceApplication.class, args);
    }
}
