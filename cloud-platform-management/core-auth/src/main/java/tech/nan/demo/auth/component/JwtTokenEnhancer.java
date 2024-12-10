package tech.nan.demo.auth.component;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import tech.nan.demo.auth.model.UserPrincipal;

import java.util.HashMap;
import java.util.Map;

public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) oAuth2Authentication.getPrincipal();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user_id", userPrincipal.getUserId());
        userInfo.put("user_name", userPrincipal.getUsername());
        userInfo.put("user_roles", userPrincipal.getRoles());
        userInfo.put("group_id", userPrincipal.getGroupId());
        userInfo.put("group_name", userPrincipal.getGroupName());
        userInfo.put("org_id", userPrincipal.getOrgId());
        userInfo.put("org_name", userPrincipal.getOrgName());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(userInfo);
        return oAuth2AccessToken;
    }
}
