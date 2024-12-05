package tech.nan.demo.gateway.component;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tech.nan.demo.gateway.constant.AuthConstant;
import tech.nan.demo.gateway.util.AesUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Bearer Token提前处理
 */
public class BearerTokenConverter extends ServerBearerTokenAuthenticationConverter {

    public static final String SPACE = " ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.fromCallable(() -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String authorization = headers.getFirst(AuthConstant.AUTHORIZATION);
            if (StringUtils.startsWithIgnoreCase(authorization, AuthConstant.BEARER_PREFIX)) {
                String[] s = authorization.split(SPACE);
                String rawToken = s[1];
                if (!isJwt(rawToken)) {
                    request.mutate().header(AuthConstant.AUTHORIZATION, AuthConstant.BEARER_PREFIX
                            +
                            AesUtils.decrypt(URLDecoder.decode(rawToken, StandardCharsets.UTF_8.name())));
                }
            }
            return exchange;
        }).onErrorMap(e -> {
            BearerTokenError error = BearerTokenErrors.invalidToken("token error.");
            return new OAuth2AuthenticationException(error);
        }).flatMap(super::convert);
    }

    private boolean isJwt(String token) {
        try {
            JwtHelper.decode(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
