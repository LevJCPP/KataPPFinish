package ru.levjcpp.katappfinish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.UserService;
import ru.levjcpp.katappfinish.util.UserValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public AuthController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
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
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }

        userService.save(user);
        return "redirect:/auth/login";
    }
}
