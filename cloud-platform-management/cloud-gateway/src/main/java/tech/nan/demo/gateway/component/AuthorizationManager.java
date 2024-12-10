package tech.nan.demo.gateway.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import tech.nan.demo.gateway.constant.AuthConstant;

import java.net.URI;

@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthorizationManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMap(authenticated -> {
                    String auth = (String) redisTemplate.opsForValue().get(String.format(AuthConstant.AUTH_URL_PREFIX, uri.getPath()));
                    if (!StringUtils.hasText(auth)) {
                        return Mono.just(new AuthorizationDecision(true));
                    }
                    boolean hasAuthority = authenticated.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .anyMatch(auth::equals);
                    // 403
                    if (!hasAuthority) {
                        return Mono.error(new AccessDeniedException("Access denied"));
                    }
                    return Mono.just(new AuthorizationDecision(true));
                })
                // 401
                .switchIfEmpty(Mono.defer(() -> Mono.just(new AuthorizationDecision(false))));
    }
}
