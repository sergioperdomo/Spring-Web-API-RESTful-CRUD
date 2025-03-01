package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    @Size(min = 4, max = 20, message = "{userDto.username.size}")
    @NotBlank(message = "{userDto.username.notBlank}")
    private String username;

    @Size(min = 7, message = "{userDto.password.size}")
    @NotBlank(message = "{userDto.password.notBlank}")
    @JsonInclude(JsonInclude.Include.NON_NULL) //Oculta la contrasela del JSON de respuesta, cuando es null.
    private String password;
    private List<Role> roles;

    private boolean admin;

}
