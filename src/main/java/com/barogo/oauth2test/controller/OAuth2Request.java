package com.barogo.oauth2test.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OAuth2Request {

  private String grant_type;

  private String client_id;

  private String client_secret;

  private String username;

  private String password;


}
