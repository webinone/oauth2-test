package com.barogo.oauth2test.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/health")
public class HealthController {


  @GetMapping(value = "/check")
  public String findAllUser() {
    return "OK";
  }
}
