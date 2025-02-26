package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User save(User user);
}
