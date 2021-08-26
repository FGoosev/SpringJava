package com.fgoosev.Blog.repos;

import com.fgoosev.Blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
