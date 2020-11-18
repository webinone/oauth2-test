package com.barogo.oauth2test.config;

import java.util.Map;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

  @Override
  protected Map<String, Object> decode(String token) {
    return super.decode(token);
  }

  public Map<String, Object> decodeToken(String token) {
    return super.decode(token);
  }
}
