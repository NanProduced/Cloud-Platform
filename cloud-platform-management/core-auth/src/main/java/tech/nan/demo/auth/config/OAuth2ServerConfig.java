package tech.nan.demo.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import tech.nan.demo.auth.component.JwtEncryptTokenConverter;
import tech.nan.demo.auth.component.JwtTokenEnhancer;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

@EnableAuthorizationServer
@Configuration
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userServiceImpl;

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${demo.security.jwt-key}")
    private String jwt_key;

    @Value("${demo.security.jwt-alias}")
    private String jwt_alias;

    public OAuth2ServerConfig(AuthenticationManager authenticationManager, UserDetailsService userServiceImpl, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userServiceImpl = userServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("front_end_client")
                .secret(passwordEncoder.encode("Nan12091209"))
                .scopes("terminal,group,user")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600)
                .and()
                .withClient("client_grant")
                .secret(passwordEncoder.encode("Nan12091209"))
                .scopes("terminal,group,user")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600)
                .redirectUris("https://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(jwtTokenEnhancer());
        enhancers.add(jwtEncryptTokenConverter());
        enhancerChain.setTokenEnhancers(enhancers);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userServiceImpl)
                .accessTokenConverter(jwtEncryptTokenConverter())
                .tokenEnhancer(enhancerChain);
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

    @Bean
    public JwtEncryptTokenConverter jwtEncryptTokenConverter() {
        JwtEncryptTokenConverter jwtEncryptTokenConverter = new JwtEncryptTokenConverter();
        jwtEncryptTokenConverter.setKeyPair(keyPair());
        return jwtEncryptTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("demo-jwt.jks"), jwt_key.toCharArray());
        return keyStoreKeyFactory.getKeyPair(jwt_alias, jwt_key.toCharArray());
    }
}
