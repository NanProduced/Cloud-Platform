package tech.nan.demo.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import tech.nan.demo.gateway.component.AuthorizationManager;
import tech.nan.demo.gateway.component.BearerTokenConverter;
import tech.nan.demo.gateway.component.RestAuthenticationEntryPoint;
import tech.nan.demo.gateway.component.RestfulAccessDeniedHandler;
import tech.nan.demo.gateway.constant.AuthConstant;
import tech.nan.demo.gateway.filter.RemoveJwtFilter;


@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RemoveJwtFilter removeJwtFilter;
    private final IgnoreUrlsConfig ignoreUrlsConfig;
    private final AuthorizationManager authorizationManager;

    @Bean
    @RefreshScope
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        // 自定义JWT请求头过期或签名错误返回
        httpSecurity.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
        // 通过身份验证后移除JWT请求头
        httpSecurity.addFilterAfter(removeJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        httpSecurity.authorizeExchange()
                .pathMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0])).permitAll()
                .anyExchange().access(authorizationManager)
                .and()
                .oauth2ResourceServer(oauth2 -> oauth2.authenticationEntryPoint(restAuthenticationEntryPoint)
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .bearerTokenConverter(new BearerTokenConverter()))
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().csrf().disable();
        return httpSecurity.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
