package org.gatewayservice.system;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class CorsResponseHeaderFilter extends AbstractGatewayFilterFactory<CorsResponseHeaderFilter.Config> implements GlobalFilter, Ordered {

    public CorsResponseHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
        }));
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(exchange.getResponse()+"====================");
        // Set a breakpoint here to inspect the exchange
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100; // High precedence
    }

    public static class Config {
    }
}