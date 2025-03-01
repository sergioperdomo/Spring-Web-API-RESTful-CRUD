package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/***
 * ¿Cuándo se usa AuthenticationEntryPoint?
 * 1. Autenticaciónn basada en formularios(form-login): Redirige a una página de login.
 * 2. Autenticación con tokens(JWT, OAuth, etc): Devuelve un error 401 Unauthorized.
 * 3. Autentication con Basic Auth: Envía el encabezado www-Authenticate.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //    Este método se usa para manejar intentos de acceso no autenticados, ya sea redirigiendo al usario, delvoviendo un error HTTP o iniciando un flujo de autenticación.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
