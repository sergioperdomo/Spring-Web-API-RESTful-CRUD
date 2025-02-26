package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.User;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
