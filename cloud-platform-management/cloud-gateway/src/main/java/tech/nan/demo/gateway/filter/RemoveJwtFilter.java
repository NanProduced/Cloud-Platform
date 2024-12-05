package tech.nan.demo.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import tech.nan.demo.gateway.constant.AuthConstant;

/**
 * 白名单路径访问时需要移除JWT请求头
 */
@Component
public class RemoveJwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(AuthConstant.AUTHORIZATION, (String) null).build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }
}
