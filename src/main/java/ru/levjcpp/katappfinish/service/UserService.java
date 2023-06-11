package ru.levjcpp.katappfinish.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.levjcpp.katappfinish.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(User user);
    void deleteUserById(Long id);
    User findById(Long id);
    Collection<User> findAll();
    Optional<User> findUserByUsername(String username);
}
