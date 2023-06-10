package ru.levjcpp.katappfinish.service;

import ru.levjcpp.katappfinish.model.Role;

import java.util.Collection;

public interface RoleService {

    Role getByName(String name);
    Collection<Role> findAll();
}
