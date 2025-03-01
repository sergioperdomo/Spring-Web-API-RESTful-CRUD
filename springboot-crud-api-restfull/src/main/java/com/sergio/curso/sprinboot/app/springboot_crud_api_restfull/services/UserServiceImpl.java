package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.services;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.DtoAuthResponse;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos.UserDto;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Role;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.User;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.RoleRepository;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.UserRepository;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security.JwtGenerador;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtGenerador jwtGenerador;


    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtGenerador jwtGenerador) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerador = jwtGenerador;
    }

    //    Buscar por nombre
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::convertToDTO);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<DtoAuthResponse> login(UserDto userDtoLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDtoLogin.getUsername(), userDtoLogin.getPassword()));

//        Establecemos la autenticacion en el contexto de seguridad spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Generamos el token JWT
        String token = jwtGenerador.generarToken(authentication);

//        Construimos la respuesta con el token.
        DtoAuthResponse dtoAuthResponse = new DtoAuthResponse(token, "Bearer");

//        Retornamos el objeto envuelto en optional
        return Optional.of(dtoAuthResponse);
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAdmin(userDto.isAdmin());

        List<Role> roles = new ArrayList<>();

//        Asignar siempre el rol "ROLE_USERNAME" por defecto.
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(()-> new RuntimeException("Error: Rol USER no encontrado"));
        roles.add(userRole);

//        Si el usuario es admin, agrega el rol "ROLE_ADMIN"
        if (userDto.isAdmin()){
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Role ADMIN no encontrado"));
            roles.add(adminRole);
        }

//        Si el usuario proporcionó roles adicionales, los validaremos y agregamos.
        if (userDto.getRoles() != null){
            userDto.getRoles().forEach(role -> {
                Role existingRole = roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Error: Rol " + role.getName() + " no encontrado."));
                if (!roles.contains(existingRole)){
                    roles.add(existingRole);
                }
            });
        }
        user.setRoles(roles);

        return convertToDTO(userRepository.save(user));


    }

//    Convierte entidad en DTO
    private UserDto convertToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles()); // ✅ Mantén los roles

//        Calcula "admin" en base de datos.
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
        dto.setAdmin(isAdmin);
        return dto;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO) // ✅ Aplicamos la conversión correctamente
                .toList();
    }
}
