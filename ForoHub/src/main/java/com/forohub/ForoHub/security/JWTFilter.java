package com.forohub.ForoHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
@Component
public class JWTFilter extends OncePerRequestFilter {

    //@Value("${jwt.secret}")
    private String secret="123456"; // La clave secreta que usas para firmar el JWT

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtener el token del encabezado Authorization
        String token = obtenerToken(request);
        System.out.println("Token sin bearer:"+token);

        if (token != null && !token.isEmpty()) {
            try {
                // Verificar el token
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
                DecodedJWT decodedJWT = verifier.verify(token);

                // Extraer el nombre de usuario del token
                String username = decodedJWT.getSubject();

                if (username != null) {
                    // Si el token es válido, configurar el contexto de seguridad con el usuario autenticado
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>())
                    );
                }
            } catch (JWTVerificationException exception) {
                // Si el token no es válido, devolver 401 Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Continuar con el filtro (pase a la siguiente capa)
        filterChain.doFilter(request, response);
    }

    // Extraer el token del encabezado Authorization
    private String obtenerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        System.out.println(header);
        try {
            if (header != null && header.startsWith("Bearer ")) {
                return header.substring(7); // Eliminar "Bearer " y devolver el token
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el token: " + e.getMessage());
        }
        return null;
    }
}