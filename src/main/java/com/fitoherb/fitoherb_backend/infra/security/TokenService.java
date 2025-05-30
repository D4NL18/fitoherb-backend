package com.fitoherb.fitoherb_backend.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fitoherb.fitoherb_backend.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create().withIssuer("fitoherb-api").withSubject(user.getEmail()).withExpiresAt(this.generateExpirationTime()).sign(algorithm);
            return token;
        }
        catch (JWTCreationException exception){
            throw new RuntimeException("Error authenticating");
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("fitoherb-api").build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
