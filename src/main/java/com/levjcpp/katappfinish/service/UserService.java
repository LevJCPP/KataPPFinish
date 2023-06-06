package com.levjcpp.katappfinish.service;

import com.levjcpp.katappfinish.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void deleteById(Long id);
    User findById(Long id);
    List<User> findAll();
}