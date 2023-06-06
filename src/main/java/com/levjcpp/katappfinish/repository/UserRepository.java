package com.levjcpp.katappfinish.repository;

import com.levjcpp.katappfinish.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
