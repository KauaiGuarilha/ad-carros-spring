package com.carros.repository;

import com.carros.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
