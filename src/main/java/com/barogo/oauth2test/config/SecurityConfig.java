package com.barogo.oauth2test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomAuthenticationProvider customAuthenticationProvider;

//  @Bean
//  public PasswordEncoder noOpPasswordEncoder() {
//    return NoOpPasswordEncoder.getInstance();
//  }


  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication().passwordEncoder(noOpPasswordEncoder()).
//    auth.inMemoryAuthentication().
//        withUser("user").password("{noop}pass").roles("USER");
    auth.authenticationProvider(customAuthenticationProvider);
  }

//  @Bean
//  @Override
//  public UserDetailsService userDetailsService() {
//
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//    final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
//    UserDetails user = userBuilder
//        .username("user")
//        .password("pass")
//        .roles("USER")
//        .build();
//
//    UserDetails admin = userBuilder
//        .username("admin")
//        .password("pass")
//        .roles("USER","ADMIN")
//        .build();
//
//    return new InMemoryUserDetailsManager(user, admin);
//  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/oauth/**", "/oauth/token", "/oauth2/**", "/oauth2/token", "/h2-console/*").permitAll()
        .antMatchers("/api/v1/health/check").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/api/v1/health/check", "/oauth2/**", "/oauth2/token");
  }
}
