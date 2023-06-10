package ru.levjcpp.katappfinish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.RoleService;
import ru.levjcpp.katappfinish.service.UserService;
import ru.levjcpp.katappfinish.util.UserValidator;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RoleService roleService;
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AuthController(RoleService roleService,
                          UserService userService,
                          UserValidator userValidator) {
        this.roleService = roleService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") User user) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        // Временный костыль, чтобы вручную в бд не добавлять и не менять роли
        String roleName;
        if (user.getUsername().equals("admin")) {
            roleName = "Admin";
        } else {
            roleName = "User";
        }
        user.setRoles(List.of(roleService.getByName(roleName)));

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }

        userService.save(user);
        return "redirect:/auth/login";
    }
}
