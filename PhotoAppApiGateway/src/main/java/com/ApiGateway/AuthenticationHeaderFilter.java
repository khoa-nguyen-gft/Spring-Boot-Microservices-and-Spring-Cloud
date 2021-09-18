package com.ApiGateway;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthenticationHeaderFilter extends AbstractGatewayFilterFactory {

    private Environment environment;

    private static class constraints{
        private static final String AUTHORIZATION = "authorization";
        private static final String BEARER = "Bearer";

    }

    @Autowired
    public AuthenticationHeaderFilter(Environment environment) {
        this.environment = environment;
    }



    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>GatewayFilter: " + request.toString());

            if(!request.getHeaders().containsKey(constraints.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            String authorization = request.getHeaders().get(constraints.AUTHORIZATION).get(0);

            String jwt = authorization.replaceAll(constraints.BEARER , "").replaceAll(" ", "");

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>jwt: " + jwt);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>environment.getProperty(\"token.secret\"): " + environment.getProperty("token.secret"));

            if(!validateJwt(jwt)){
                return onError(exchange, "The JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private boolean validateJwt(String jwt) {
        String subject = null;
        try{
            subject = Jwts.parser()
                    .setSigningKey(environment.getProperty("token.secret"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>subject: " + subject);
        } catch( Exception e){
            return false;
        }


        if (subject == null || subject.isEmpty()) {
            return false;
        }

        return true;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String no_authorization_header, HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        return response.setComplete();
    }


}
