package com.forohub.ForoHub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.forohub.ForoHub.CustomUserDetails;
import com.forohub.ForoHub.models.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {




    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;


    public String generarToken(Authentication authentication){
        // Primero se hace el cast a CustomUserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // Luego accedes al Usuario desde CustomUserDetails
        Usuario usuario = customUserDetails.getUsuario();

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);//no dejar expuesto
            return JWT.create()
                    .withSubject(usuario.getUsername())
                    .withIssuedAt(new Date())  // Fecha de emisión
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))  // Expiración de 24 horas
                    .sign(algorithm);  // Firmamos el JWT con el algoritmo
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al crear el token JWT", exception);
        }
    }
}
