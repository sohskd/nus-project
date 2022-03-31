package com.nus.team3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Controller
@EnableSwagger2
@SpringBootApplication(exclude = { ContextStackAutoConfiguration.class })
public class OrderMatchingServiceApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World Order!";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(OrderMatchingServiceApplication.class, args);
    }
}
