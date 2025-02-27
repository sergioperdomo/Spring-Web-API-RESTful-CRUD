package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;


@Component
public class ConstanteSeguridad {

    public static long JWT_EXPIRATION_TOKEN; // 5 minutos.
    public static Key JWT_FIRMA;

    @Value("${jwt.expiration}")
    public void setJwtExpirationToken(long expirationToken){
        JWT_EXPIRATION_TOKEN = expirationToken;
    }

    @Value("${jwt.secret}")
    public void setJwtFirma(String secretKey){
        JWT_FIRMA = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

}
