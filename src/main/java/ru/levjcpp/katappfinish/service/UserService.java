package ru.levjcpp.katappfinish.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.levjcpp.katappfinish.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(User user);
    void deleteUserById(Long id);
    User findById(Long id);
    List<User> findAll();
    Optional<? extends UserDetails> findUserByUsername(String username);
}
