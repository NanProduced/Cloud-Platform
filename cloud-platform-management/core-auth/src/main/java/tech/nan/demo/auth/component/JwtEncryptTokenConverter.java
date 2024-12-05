package tech.nan.demo.auth.component;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import tech.nan.demo.auth.util.AesUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JwtEncryptTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) super.enhance(accessToken, authentication);
        DefaultExpiringOAuth2RefreshToken defaultRefreshToken = (DefaultExpiringOAuth2RefreshToken) defaultAccessToken.getRefreshToken();
        String encryptAccessToken = AesUtils.encrypt(defaultAccessToken.getValue());
        String encryptRefreshToken = AesUtils.encrypt(defaultRefreshToken.getValue());
        try {
            defaultAccessToken.setValue(URLEncoder.encode(encryptAccessToken, StandardCharsets.UTF_8.name()));
            defaultAccessToken.setRefreshToken(new DefaultExpiringOAuth2RefreshToken(URLEncoder.encode(encryptRefreshToken, StandardCharsets.UTF_8.name()),
                    defaultRefreshToken.getExpiration()));
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return defaultAccessToken;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        if (isJwt(value)) {
            return super.extractAccessToken(value, map);
        }
        return super.extractAccessToken(AesUtils.decrypt(value), map);
    }

    @Override
    protected Map<String, Object> decode(String token) {
        if (isJwt(token)) {
            return super.decode(token);
        }
        return super.decode(AesUtils.decrypt(token));
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
