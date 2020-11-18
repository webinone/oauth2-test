package com.barogo.oauth2test.service;

import com.barogo.oauth2test.domain.entity.User;
import com.barogo.oauth2test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = this.userRepository.findByUid(username).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));

    return user;
  }
}
