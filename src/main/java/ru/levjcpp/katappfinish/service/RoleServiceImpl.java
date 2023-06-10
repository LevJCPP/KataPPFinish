package ru.levjcpp.katappfinish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.levjcpp.katappfinish.model.Role;
import ru.levjcpp.katappfinish.repository.RoleRepository;

import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role getByName(String name) {
        Role role = roleRepository.findByName(name).orElse(null);

        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }

        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
}
