package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomUsersDetailsService customUsersDetailsService;
    private final JwtGenerador jwtGenerador;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, CustomUsersDetailsService customUsersDetailsService, JwtGenerador jwtGenerador){
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUsersDetailsService = customUsersDetailsService;
        this.jwtGenerador = jwtGenerador;
    }

//    Este bean va a encargarse de verificar la información de los usuarios que se loguearan en nuestra API
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

//    Con este bean nos encargaremos de encriptar las contraseñas.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    Incorporará el filtro de segyridad de JWT que se creo en la clase JwtAuthenticationFilter
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(customUsersDetailsService, jwtGenerador);
    }

//    Establece una cadena de filtros de seguridad en nuestra aplicación. Es  aquí donde determinaremos los permisos segun los roles de usuarios para acceder a nuestra API
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable()) // ✅ Deshabilita CSRF para permitir POST
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers( "/api/auth/user/login","/api/auth/user/create").permitAll() // ✅ Permite acceso sin autenticación
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .build();
    }
}
