package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.validators;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.UserDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class UserValidation implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof UserDto userDto){
            validationUser(userDto, errors);
        }
    }

    private  void validationUser(UserDto userDto, Errors errors){

        if (userDto.getUsername() == null || userDto.getUsername().trim().isEmpty()){
            errors.rejectValue("username", "userDto.username.notBlank", messageSource.getMessage("userDto.username.notBlank", null, Locale.getDefault()));
        }else if(userDto.getUsername().length() < 4 || userDto.getUsername().length() > 20){
            errors.rejectValue("username", "userDto.username.size", messageSource.getMessage("userDto.username.size", null, Locale.getDefault()));
        }
        if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()){
            errors.rejectValue("password","userDto.password.notBlank", messageSource.getMessage("userDto.password.notBlank", null, Locale.getDefault()));
        }else if(userDto.getPassword().length() < 7){
            errors.rejectValue("password", "userDto.password.size",messageSource.getMessage("userDto.password.size", null, Locale.getDefault()));
        }
    }


}
