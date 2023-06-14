package ru.levjcpp.katappfinish.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.levjcpp.katappfinish.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(User user);

    User findById(Long id);

    List<User> findAll();

    Optional<User> findUserByUsername(String username);

    void deleteUserById(Long id);

    @Override
    User loadUserByUsername(String username);
}
