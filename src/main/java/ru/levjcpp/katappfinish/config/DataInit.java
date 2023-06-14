package ru.levjcpp.katappfinish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.levjcpp.katappfinish.model.Role;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.RoleService;
import ru.levjcpp.katappfinish.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataInit {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void dataInit() {
        Role adminRole = new Role();
        if (roleService.findByName("Admin") == null) {
            adminRole.setName("Admin");
            roleService.save(adminRole);

            if (userService.findUserByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setFirstName("admin first");
                admin.setLastName("admin last");
                admin.setYearOfBirth(1950);
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setRoles(List.of(adminRole));
                userService.save(admin);
            }
        }
        Role userRole = new Role();
        if (roleService.findByName("User") == null) {
            userRole.setName("User");
            roleService.save(userRole);

            if (userService.findUserByUsername("user").isEmpty()) {
                User user = new User();
                user.setFirstName("user first");
                user.setLastName("user last");
                user.setYearOfBirth(2000);
                user.setUsername("user");
                user.setPassword("user");
                user.setRoles(List.of(userRole));
                userService.save(user);
            }
        }
    }
}
