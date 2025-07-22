package com.finance.FinancialApp.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// @Component tells to Spring that this class is bean (BEAN - any validated object of Spring Framework) 
@Component
public class JwtUtil {
    // secret key for token sign
    private static final String SECRET_KEY = "secure-key-for-jwt-token-which-should-be-long-enough";

    // token lifetime
    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 24;

    // creates a key object
    private Key getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    // gets login from token
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // gets an expirationtime of token
    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // gets ANY infromation from the token
    public <T> T extractClaim(final String token, final Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // extracts all the token contains
    public Claims extractAllClaims(final String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSignInKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    // puts login into the subjec, sets creation date and expiration date, signs the token
    public String generateToken(final String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    // checks if the usernames both of token and user are equal
    public boolean isTokenValid(final String token, final String username) {
        final String extractUsername = extractUsername(token);
        return (extractUsername.equals(username) && !isTokenExpired(token));
    }

    // checks if the token has been expired
    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token is encoded part of data that is used for autorithation and auntetification of user 
}
