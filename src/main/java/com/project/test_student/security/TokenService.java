package com.project.test_student.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.test_student.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_SECRET}")
    private String secretKey;

    public String generate(User user){

        var algorithm = Algorithm.HMAC256(secretKey);

        try {
           return JWT.create()
                    .withIssuer("LuisFelipe")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExperiesToken())
                    .sign(algorithm);
        }
        catch (JWTCreationException e){
            throw new RuntimeException("Erro While Generating Token", e);
        }
    }

    private Instant genExperiesToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String tokenToValidate){

        try {
            var algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("LuisFelipe")
                    .build()
                    .verify(tokenToValidate)
                    .getSubject();
        }
        catch (JWTVerificationException exception)
        {
            throw new RuntimeException("Error While Verification of token", exception);
        }
    }
}
