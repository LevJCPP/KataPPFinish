package ru.levjcpp.katappfinish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.RoleService;
import ru.levjcpp.katappfinish.service.UserService;
import ru.levjcpp.katappfinish.util.UserValidator;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @ModelAttribute
    public void modelAttributes(Model model, Principal authUser) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new User());
        model.addAttribute("editUser", new User());
        model.addAttribute("authUser", userService.findUserByUsername(authUser.getName()).orElse(null));
    }

    @GetMapping
    public String getAdmin() {
        return "admin";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute User editUser, BindingResult bindingResult) {
        userValidator.validateUpdate(editUser, bindingResult, true);

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        userService.save(editUser);
        return "redirect:/admin";
    }

    @PostMapping("/")
    public String newUser(Model model, @ModelAttribute User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("newUser", user);
            System.out.println(bindingResult.getAllErrors());
            return "admin";
        }

        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, @ModelAttribute User authUser) {
        userService.deleteUserById(id);
        if (authUser.getId().equals(id)) {
            return "redirect:/logout";
        }
        return "redirect:/admin";
    }
}
