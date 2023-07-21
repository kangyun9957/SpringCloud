package com.example.apigatewayservice.filter;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    private static final String FOO_KEY = "foo";

    public CustomGatewayFilterFactory() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter: request Id -> {}",request.getId());
            log.info("Get FOO -> {}",config.getFoo());
            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                log.info("Custom PRE filter: request Id -> {}",response.getStatusCode());
            }));
        });
    }


    @Builder
    @Data
    public static class Config{
        // Put the configuration properties
        public String foo;
    }
}
