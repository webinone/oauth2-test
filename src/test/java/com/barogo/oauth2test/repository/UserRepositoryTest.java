package com.barogo.oauth2test.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.barogo.oauth2test.config.OAuth2AuthorizationServerConfig;
import com.barogo.oauth2test.config.SecurityConfig;
import com.barogo.oauth2test.config.WebMvcConfig;
import com.barogo.oauth2test.domain.entity.User;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DataJpaTest
@ActiveProfiles("localhost")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  @Test
  public void insertNewUser() {

    passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    this.userRepository.save(
        User.builder()
        .uid("foresight")
        .password(passwordEncoder.encode("1234"))
        .name("장영조")
        .roles(Collections.singletonList("ROLE_USER"))
        .build()
    );
  }

  @Test
  void findByUid() {

    Optional<User> optionalUser = this.userRepository.findByUid("foresight");

    assertTrue(optionalUser.isPresent());
  }
}