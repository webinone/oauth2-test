package com.barogo.oauth2test.controller;

import com.barogo.oauth2test.config.CustomJwtAccessTokenConverter;
import com.barogo.oauth2test.domain.model.OAuthToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

  private final Gson gson;
  private final RestTemplate restTemplate;
  private final CustomJwtAccessTokenConverter customJwtAccessTokenConverter;

  @GetMapping(value = "/callback")
  public OAuthToken callbackSocial(@RequestParam String code) {

    log.info(">>>>>>>> code : " + code);

    String credentials = "testClientId:testSecret";
    String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Authorization", "Basic " + encodedCredentials);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("code", code);
    params.add("grant_type", "authorization_code");
    params.add("redirect_uri", "http://localhost:8888/oauth2/callback");
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8888/oauth/token", request, String.class);
    if (response.getStatusCode() == HttpStatus.OK) {
      return gson.fromJson(response.getBody(), OAuthToken.class);
    }
    return null;
  }

  @GetMapping(value = "/token/refresh")
  public OAuthToken refreshToken(@RequestParam String refreshToken) throws Exception {

    String credentials = "testClientId:testSecret";
    String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

    // TODO : jwt parsing 해서 확인 한다.
    // ------------------------------------

    log.info(refreshToken);

//    jwtAccessTokenConverter.getJwtClaimsSetVerifier().

    log.info(">>>>>>>>>>> refresh token >>>>>>>>>>>> !!");
    log.info(">>>>>>>>>>> refresh token text : " + refreshToken);
//    customJwtAccessTokenConverter.decodeToken(refreshToken);

    Jwt jwt = JwtHelper.decode(refreshToken);
    String claims = jwt.getClaims();

    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> map = mapper.readValue(claims, Map.class);

    String userName = map.get("user_name");
    log.info(">>>>>>>>>>>>> userName : "  + userName);


    // ------------------------------------

    log.info(">>>>>>>>>>>>>>>>> 여기까지는 왔다 !!!!!!!!!!!!!!!!!!!!!!!!!!123444");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.add("Authorization", "Basic " + encodedCredentials);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("refresh_token", refreshToken);
    params.add("grant_type", "refresh_token");
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8888/oauth/token", request, String.class);
    if (response.getStatusCode() == HttpStatus.OK) {
      return gson.fromJson(response.getBody(), OAuthToken.class);
    }
    return null;
  }

  @PostMapping(value = "/token")
  public OAuthToken token (OAuth2Request oAuth2Request) {

    log.info(">>>>>>>> grant_type : " + oAuth2Request.getGrant_type());
    log.info(">>>>>>>> client_id : " + oAuth2Request.getClient_id());
    log.info(">>>>>>>> client_secret : " + oAuth2Request.getClient_secret());
    log.info(">>>>>>>> username : " + oAuth2Request.getUsername());
    log.info(">>>>>>>> password : " + oAuth2Request.getPassword());


    // TODO : DataService를 통해서 데이터를 가져온다.
    // ------------------------------------

    // ------------------------------------

    StringBuilder stringBuilder = new StringBuilder();

    String credentials = stringBuilder.append(oAuth2Request.getClient_id())
                                      .append(":")
                                      .append(oAuth2Request.getClient_secret()).toString();

    String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.add("Authorization", "Basic " + encodedCredentials);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("username", oAuth2Request.getUsername());
    params.add("password", oAuth2Request.getPassword());
    params.add("client_id", oAuth2Request.getClient_id());
    params.add("client_secret", oAuth2Request.getClient_secret());
    params.add("grant_type", "password");
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8888/oauth/token", request, String.class);
    if (response.getStatusCode() == HttpStatus.OK) {
      return gson.fromJson(response.getBody(), OAuthToken.class);
    }

    return null;

  }
}
