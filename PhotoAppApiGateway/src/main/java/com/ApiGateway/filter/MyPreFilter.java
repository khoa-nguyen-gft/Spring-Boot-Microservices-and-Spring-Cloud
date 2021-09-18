package com.ApiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class MyPreFilter implements GlobalFilter {


    private static final Logger logger = LoggerFactory.getLogger(MyPreFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();

        Set<String> keyHeader = headers.keySet();

        keyHeader.forEach(key ->{
            String headerValue = headers.getFirst(key);
        });

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>PRE-MyPreFilter");
        return chain.filter(exchange);
    }
}
