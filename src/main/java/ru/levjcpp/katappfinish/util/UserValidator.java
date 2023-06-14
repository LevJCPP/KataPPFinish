package ru.levjcpp.katappfinish.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.UserService;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.findUserByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "User with such username already exists");
            return;
        }

        if (user.getPassword().isEmpty()) {
            errors.rejectValue("password", "", "Required field");
        }

        validateUpdate(user, errors, false);
    }

    public void validateUpdate(User user, Errors errors, boolean validateUsername) {
        if (validateUsername && !userService.findUserByUsername(user.getUsername()).orElse(user)
                .getId().equals(user.getId())) {
            errors.rejectValue("username", "", "User with such username already exists");
            return;
        }

        if (user.getUsername().isEmpty()) {
            errors.rejectValue("username", "", "Required field");
        }

        if (!user.getPassword().isEmpty() && (user.getPassword().length() < 4)) {
            errors.rejectValue("password", "", "Password must be at least 4 characters long");
        }

        if (user.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "", "Required field");
        }

        if (user.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "", "Required field");
        }

        if ((user.getYearOfBirth() == null) || ((user.getYearOfBirth() < 1923) || (user.getYearOfBirth() > 2005))) {
            errors.rejectValue("yearOfBirth", "", "Invalid year of birth");
        }

        if ((user.getRoles() == null) || (user.getRoles().isEmpty())) {
            errors.rejectValue("roles", "", "Required field");
        }
    }
}
