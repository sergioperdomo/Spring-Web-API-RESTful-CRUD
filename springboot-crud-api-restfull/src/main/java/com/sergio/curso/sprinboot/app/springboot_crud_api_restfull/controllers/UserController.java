package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.controllers;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.DtoAuthResponse;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.UserDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.User;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services.UserService;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.validators.UserValidation;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/user")
public class UserController {


    private final UserService userService;
    private final UserValidation userValidation;

    public UserController(UserService userService, UserValidation userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidation);
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<?> checkUserExists(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(Map.of("exists", exists));

    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok) // Devuelve ResponseEntity<UserDto>
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((UserDto) Map.of("error", "Usuario con username '" + username + "' no encontrado"))); //  Devuelve un Map<String, String>
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto, BindingResult result) {
//        Validar errores en los campos del DTO
        if (result.hasFieldErrors()) {
            return validation(result);
        }

//        Verificar si el usuario ya existe

        if (userService.existsByUsername(userDto.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "El usuario '" + userDto.getUsername() + "' ya existe."));
        }
        try {
            UserDto savedUser = userService.save(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No se pudo crear el usuario: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        Optional<DtoAuthResponse> response = userService.login(userDto);
        return response
                .map(dtoAuthResponse -> ResponseEntity.ok().body(dtoAuthResponse))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    //    Método de validación
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), String.format("Error en el campo '%s':%s", error.getField(), error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
