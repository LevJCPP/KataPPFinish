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
        try {
            Role adminRole = new Role();
            adminRole.setName("Admin");
            roleService.save(adminRole);

            Role userRole = new Role();
            userRole.setName("User");
            roleService.save(userRole);

            User admin = new User();
            admin.setRoles(List.of(adminRole));
            userService.save(admin);

            User user = new User();
            user.setRoles(List.of(userRole));
            userService.save(user);
        } catch (IllegalArgumentException e) {
        }
    }
}
