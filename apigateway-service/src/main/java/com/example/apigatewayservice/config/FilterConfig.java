package com.example.apigatewayservice.config;

import com.example.apigatewayservice.filter.AuthorizationHeaderFilter;
import com.example.apigatewayservice.filter.CustomGatewayFilterFactory;
import com.example.apigatewayservice.filter.GlobalFilter2;
import com.example.apigatewayservice.filter.LoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.RemoveRequestHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final CustomGatewayFilterFactory customGatewayFilterFactory;

    private final LoggingFilter loggingFilter;

    private final AuthorizationHeaderFilter authorizationHeaderFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {


        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f
                                .addRequestHeader("first-request", "first-request-header")
                                .addRequestHeader("first-response", "first-response-header")
                                .filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build())))
                        .uri("lb://MY-FIRST-SERVICE"))
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addRequestHeader("second-response", "second-response-header")
                                .filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build())))
                        .uri("lb://MY-SECOND-SERVICE"))
                .route(r -> r.method(HttpMethod.POST).and().path("/user-service/login")
                        .filters(f -> f.filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build()))
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)","/$\\{segment}"))
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/user-service/users")
                        .filters(f -> f.filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build()))
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)","/$\\{segment}"))
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.method(HttpMethod.GET).or().method(HttpMethod.POST).and().path("/user-service/actuator/**")
                        .filters(f -> f.filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build()))
                                .filters(authorizationHeaderFilter.apply(AuthorizationHeaderFilter.Config.builder().build()))
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)","/$\\{segment}"))
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.method(HttpMethod.GET).and().path("/user-service/**")
                        .filters(f -> f.filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build()))
                                .filters(authorizationHeaderFilter.apply(AuthorizationHeaderFilter.Config.builder().build()))
                                .removeRequestHeader("Cookie")
                                .rewritePath("/user-service/(?<segment>.*)","/$\\{segment}"))
                        .uri("lb://USER-SERVICE"))
                .route(r -> r.path("/catalog-service/**")
                        .filters(f -> f.filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build())))
                        .uri("lb://CATALOG-SERVICE"))
                .route(r -> r.path("/order-service/**")
                        .filters(f -> f.filters(customGatewayFilterFactory.apply(CustomGatewayFilterFactory.Config.builder()
                                        .foo("A")
                                        .build()))
                                .filters(loggingFilter.apply(LoggingFilter.Config.builder()
                                        .baseMessage("Hi there.")
                                        .preLogger(true)
                                        .postLogger(true)
                                        .build())))
                        .uri("lb://ORDER-SERVICE"))
                .build();
    }


}
