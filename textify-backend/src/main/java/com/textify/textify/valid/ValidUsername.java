package com.textify.textify.valid;

import com.textify.textify.entity.UniqueUsername;
import com.textify.textify.entity.User;
import com.textify.textify.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidUsername implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    UserRepo userRepo;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        User inDB = userRepo.findByUsername(value);
        return inDB == null;
    }

}

