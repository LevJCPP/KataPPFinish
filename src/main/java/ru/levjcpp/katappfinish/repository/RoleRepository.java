package ru.levjcpp.katappfinish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.levjcpp.katappfinish.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
