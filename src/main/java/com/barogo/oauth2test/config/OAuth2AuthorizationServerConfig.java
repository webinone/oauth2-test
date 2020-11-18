package com.barogo.oauth2test.config;

import com.barogo.oauth2test.service.CustomUserDetailService;
import java.util.Arrays;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final DataSource dataSource;
  private final CustomUserDetailService customUserDetailService;
  private final AuthenticationManager authenticationManager;

  @Value("${security.oauth2.jwt.signkey}")
  private String signKey;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
//    clients.inMemory()
//        .withClient("testClientId")
//        .secret("{noop}testSecret")
//        .redirectUris("http://localhost:8888/oauth2/callback")
//        .authorizedGrantTypes("authorization_code")
//        .scopes("read", "write")
//        .accessTokenValiditySeconds(30000)
//        .refreshTokenValiditySeconds(300000000);
  }

  /**
   * 토큰 정보를 DB를 통해 관리한다.
   *
   * @return
   */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource));
//    }

  /**
   * 토큰 발급 방식을 JWT 토큰 방식으로 변경한다. 이렇게 하면 토큰 저장하는 DB Table은 필요가 없다.
   *
   * @param endpoints
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

    endpoints
        .tokenEnhancer(enhancerChain)
        .authenticationManager(authenticationManager)
        .accessTokenConverter(jwtAccessTokenConverter())
        .userDetailsService(customUserDetailService);
  }

  /**
   * jwt converter를 등록
   *
   * @return
   */
  @Bean
  public CustomJwtAccessTokenConverter jwtAccessTokenConverter() {
    CustomJwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
    converter.setSigningKey(signKey);
    return converter;
  }

  @Bean
  public AuthTokenEnhancer tokenEnhancer() {
    return new AuthTokenEnhancer();
  }
}

