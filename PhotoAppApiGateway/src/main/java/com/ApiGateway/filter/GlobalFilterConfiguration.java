package com.ApiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {

    final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);
    @Order(1)
    @Bean
    public GlobalFilter secondFilterConfig(){
        return (exchange, chain) -> {

            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>PRE-secondFilterConfig");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>POST-secondFilterConfig");
            }));
        };
    }

    @Order(0)
    @Bean
    public GlobalFilter thirdFilterConfig(){
        return (exchangev2, chain) -> {

            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>PRE-thirdFilterConfig");
            return chain.filter(exchangev2).then(Mono.fromRunnable(()->{
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>POST-thirdFilterConfig");
            }));
        };

    }
}
