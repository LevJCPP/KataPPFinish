package ru.levjcpp.katappfinish.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.levjcpp.katappfinish.model.User;
import ru.levjcpp.katappfinish.service.UserServiceImpl;

@Component
public class UserValidator implements Validator {

    private final UserServiceImpl userDetailsService;

    @Autowired
    public UserValidator(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!userDetailsService.findUserByUsername(user.getUsername()).isEmpty()) {
            errors.rejectValue("username", "", "User with such username already exists");
            return;
        }

        if (user.getUsername().isEmpty()) {
            errors.rejectValue("username", "", "Required field");
        }

        if (user.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "", "Required field");
        }

        if (user.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "", "Required field");
        }

        if ((user.getYearOfBirth() < 1923) || (user.getYearOfBirth() > 2005)) {
            errors.rejectValue("yearOfBirth", "", "Invalid year of birth");
        }

        if (user.getPassword().length() < 4) {
            errors.rejectValue("password", "", "Password must be at least 4 characters long");
        }
    }
}
