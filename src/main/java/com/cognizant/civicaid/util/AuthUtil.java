package com.cognizant.civicaid.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class AuthUtil {

    @Value("${jwt.secreteKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(UserDetails userDetails){

        //getAuthorities is a collection ,so we have to convert into string
        String roles= userDetails.getAuthorities().iterator().next().getAuthority();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token){

        Claims claims=Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public String getUserNameFromToken(String token){
        return getClaims(token).getSubject();
    }

    public boolean validateBothTokenAndUserDetails(String token, UserDetails
            userDetailsFromUserDetailsService){

        Claims claims=getClaims(token);

        String userNameFromClaims=claims.getSubject();
        Date expirationDate=claims.getExpiration();

        return (userNameFromClaims !=null &&
                userNameFromClaims.equals(userDetailsFromUserDetailsService.getUsername())
                && expirationDate !=null
                && expirationDate.after(new Date())
        );
    }
}
