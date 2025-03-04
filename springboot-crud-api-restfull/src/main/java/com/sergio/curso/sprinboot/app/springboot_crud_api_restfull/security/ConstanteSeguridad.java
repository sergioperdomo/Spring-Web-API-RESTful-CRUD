package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class ConstanteSeguridad {

    public static final long  JWT_EXPIRATION_TOKEN = 300000; // 5 minutos.
    public static final String JWT_SECRET = "C9Zb3p2MfjJplX9EM9FMy87keGsXxvTbTacZZE23F9z=";
    public static final SecretKey  JWT_FIRMA = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
}
