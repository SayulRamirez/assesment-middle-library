package com.gateway.library.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${secret.key}")
    private String SECRET;

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public String generateToken(String user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, String user) {
        log.info("Generando JWT");
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(5L, ChronoUnit.HOURS)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) throws ExpiredJwtException {
        log.info("Obteniendo el username del token");
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = getUsernameFromToken(token);

        log.info("Validando el token");

        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private Date getExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}