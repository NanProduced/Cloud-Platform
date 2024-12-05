package tech.nan.demo.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tech.nan.demo.gateway.util.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 解析Token中的用户信息
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(Jwt.class)
                .map(this::analysisUserInfo)
                .map(user -> {
                    ServerHttpRequest request = exchange.getRequest().mutate().header("CLOUD-AUTH", user).build();
                    return exchange.mutate().request(request).build();
                })
                .defaultIfEmpty(exchange)
                .then(chain.filter(exchange));
    }

    private String analysisUserInfo(Jwt jwt) {
        ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("username", jwt.getClaimAsBoolean("user_name"));
        jsonNode.put("userId", (long) jwt.getClaim("user_id"));
        List<String> authorities = jwt.getClaimAsStringList("authorities");
        jsonNode.set("authorities", objectMapper.valueToTree(authorities));
        List<String>  userRoles = jwt.getClaimAsStringList("user_roles");
        jsonNode.set("userRoles", objectMapper.valueToTree(userRoles));
        return Base64Utils.encodeToString(jsonNode.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
