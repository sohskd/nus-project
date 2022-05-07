package com.nus.team3.config;


import java.lang.annotation.Annotation;

import com.nus.team3.response.Order;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi apiDocket() {
    return GroupedOpenApi.builder()
        // .apis(RequestHandlerSelectors.any())
        .group("omnitrade-orders")
        .pathsToMatch("/**")
        .build();
  }
}
