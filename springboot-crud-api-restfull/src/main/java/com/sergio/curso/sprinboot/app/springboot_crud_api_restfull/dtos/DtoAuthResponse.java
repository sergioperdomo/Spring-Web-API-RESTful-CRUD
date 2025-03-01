package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.dtos;

import lombok.Data;

// Esta clase va a ser la que nos devolvera la información con el TOKEN y el TIPO que tenga este.
@Data
public class DtoAuthResponse {

    private String accessToken;
    private String tokenType;

    public DtoAuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
