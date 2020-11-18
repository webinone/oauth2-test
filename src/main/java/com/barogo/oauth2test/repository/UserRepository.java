package com.barogo.oauth2test.repository;

import com.barogo.oauth2test.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUid(String email);
}
