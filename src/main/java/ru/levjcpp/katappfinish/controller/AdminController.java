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

    @GetMapping("/users/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new User());
        return "all-users";
    }

    @GetMapping("/users/edit/{id}")
    public String getUserEdit(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "user-edit";
    }

    @PatchMapping("/users/edit")
    public String submitEditUser(Model model, @ModelAttribute User user, BindingResult bindingResult) {
        userValidator.validateUpdate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "user-edit";
        }

        userService.update(user);
        return "redirect:/admin/users/all";
    }

    @PostMapping("users/add_user")
    public String addUser(Model model, @ModelAttribute User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("user", user);
            return "all-users";
        }

        userService.save(user);
        return "redirect:/admin/users/all";
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users/all";
    }
}
