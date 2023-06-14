package ru.levjcpp.katappfinish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @ModelAttribute
    public void modelAttributes(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new User());
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
    public String registerUser(Model model, @ModelAttribute User user, BindingResult bindingResult) {
        user.setRoles(List.of(roleService.findByName("User")));

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "/auth/register";
        }

        userService.save(user);
        return "redirect:/auth/login";
    }
}
