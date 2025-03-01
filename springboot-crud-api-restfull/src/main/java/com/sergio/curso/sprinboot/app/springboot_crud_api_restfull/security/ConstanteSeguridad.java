package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class ConstanteSeguridad {

    public static final long  JWT_EXPIRATION_TOKEN = 300000; // 5 minutos.
    public static final String JWT_SECRET = "A0/zdhyORZNijz0D0tZ9DEy9i87kfBsXGxvRbTacZZE=";
    public static final SecretKey  JWT_FIRMA = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
}
