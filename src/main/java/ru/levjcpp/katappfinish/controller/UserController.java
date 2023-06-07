package ru.levjcpp.katappfinish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userDetailsService;

    @Autowired
    public UserController(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String getUser(Principal principal, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
