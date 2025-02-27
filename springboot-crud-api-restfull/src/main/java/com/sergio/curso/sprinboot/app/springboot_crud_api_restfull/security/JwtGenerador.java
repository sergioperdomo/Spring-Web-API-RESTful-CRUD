package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtGenerador {

    //    Método para crear un token por medio de la autenticación
    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        Date realTime = new Date();
        Date expirationToken = new Date(realTime.getTime() + ConstanteSeguridad.JWT_EXPIRATION_TOKEN);


//        Construye y firma el JWT usando Jwts.builder()
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(realTime)
                .setExpiration(expirationToken)
                .signWith(ConstanteSeguridad.JWT_FIRMA) // Usamos la CLAVE SECRETA para generar la firma
                .compact();
    }
// Método para obtener el username de un token
    public String obtenerUsernameDeJwt(String token){
        Claims claims = Jwts.parserBuilder() //Crea un analizador de JWT para poder leer la inforación que contiene un token.
                .setSigningKey(ConstanteSeguridad.JWT_FIRMA) // Establece la clave para verificar la firma
                .build()
                .parseClaimsJws(token)// Verifica que el token sea valido.
                .getBody(); //Obtiene el cuerpo dek JWT (Claims)

        return claims.getSubject(); // Obtiene el usuario del token.
    }

// Método para validar el token.
    public Boolean validarToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(ConstanteSeguridad.JWT_FIRMA)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT ha expirado.");
        }

    }

}
