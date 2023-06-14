package ru.levjcpp.katappfinish.service;

import ru.levjcpp.katappfinish.model.Role;

import java.util.List;

public interface RoleService {

    void save(Role role);

    Role findByName(String name);

    List<Role> findAll();
}
