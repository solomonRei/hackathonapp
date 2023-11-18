package com.hackathon.diasporadialog.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JWTUtil {

    private final JwtEncoder encoder;

    private final JwtDecoder jwtDecoder;

    private final JWTConfigProperties jwtConfigProperties;

    public String generateAccessToken(String username) {

        var expirationTime = Date.from(Instant.now().plusSeconds(jwtConfigProperties.getExpirationTimeAccessTokenMn() * 60L));
        var claimsSet = JwtClaimsSet.builder()
                .issuer(jwtConfigProperties.getIssuer())
                .issuedAt(Instant.now())
                .expiresAt(expirationTime.toInstant())
                .subject(username)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }


    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        Jwt decodedJwt = jwtDecoder.decode(token);
        return decodedJwt.getSubject();
    }

}
