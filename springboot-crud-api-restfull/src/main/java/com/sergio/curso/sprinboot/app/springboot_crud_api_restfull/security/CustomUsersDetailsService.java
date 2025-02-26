package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.Role;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.entities.User;
import com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/***
 * UserDetailsService - Autenticación de usuarios
 * ✅ Permitir que Spring Security busque un usuario en una base de datos, API u otra fuente.
 * ✅ Proveer los detalles del usuario (credenciales y roles) en forma de un objeto UserDetails.
 * ✅ Personalizar la manera en que se recuperan los usuarios para autenticación.
 */

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Transforma los roles en permisos de seguridad
//GrantedAuthority - Es una interfaz que representa permisos o roles en Spring Security
//SimpleGrantedAuthority - Es una implementación de GrantedAuthority que almacena el rol en formato String.
    public Collection<GrantedAuthority> mapToAutorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    //Carga un usuario desde la BD y lo adapta al formato que Spring Security necesita para la autenticación.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario no encontrado: %s", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                mapToAutorities(user.getRoles())
        );
    }
}
