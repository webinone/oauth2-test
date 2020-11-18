package com.barogo.oauth2test.config;

import com.barogo.oauth2test.domain.entity.User;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {

    Map<String, Object> additionalInfo = new HashMap<>();
    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
    log.info(authentication.toString());

//    if (authentication.getUserAuthentication() != null) {
//
//      log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
//      log.info(authentication.getPrincipal().toString());
//
//
//      User user = (User) authentication.getPrincipal();
//
//      additionalInfo.put("customInfo", "some_stuff_here");
//      additionalInfo.put("authorities", user.getAuthorities());
//    }

    additionalInfo.put("customInfo", "some_stuff_here");

    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

    return accessToken;

  }
}
