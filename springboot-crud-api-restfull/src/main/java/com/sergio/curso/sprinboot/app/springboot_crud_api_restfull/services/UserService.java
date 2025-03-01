package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.DtoAuthResponse;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();
    UserDto save(UserDto userDto);
    Optional<UserDto> getUserByUsername(String username);
    boolean existsByUsername(String username);
    Optional<DtoAuthResponse> login(UserDto userDtoLogin);
}
