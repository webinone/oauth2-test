package com.barogo.oauth2test.config;

import com.barogo.oauth2test.domain.entity.User;
import com.barogo.oauth2test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userJpaRepo;

  @Override
  public Authentication authenticate(Authentication authentication) {

    String name = authentication.getName();
    String password = authentication.getCredentials().toString();

    User user = userJpaRepo.findByUid(name).orElseThrow(() -> new UsernameNotFoundException("user is not exists"));

    if (!passwordEncoder.matches(password, user.getPassword()))
      throw new BadCredentialsException("password is not valid");

    // 성공했다면..........

    return new UsernamePasswordAuthenticationToken(name, password, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(
        UsernamePasswordAuthenticationToken.class);
  }
}
