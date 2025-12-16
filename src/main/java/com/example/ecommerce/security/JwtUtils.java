//
// 
//package com.example.ecommerce.security;
// 
//import java.util.Date;
// 
//import org.springframework.beans.factory.annotation.Value;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import org.springframework.stereotype.Component;
// 
//import io.jsonwebtoken.JwtException;
//
//import io.jsonwebtoken.Jwts;
//
//import io.jsonwebtoken.security.Keys;
// 
//@Component
//
//public class JwtUtils {
// 
//    @Value("${app.jwt.secret}")
//
//    private String jwtSecret;  // MUST BE 32+ chars
// 
//    @Value("${app.jwt.expirationMs}")
//
//    private int jwtExpirationMs;
// 
//    // Generate JWT token
//
//    public String generateToken(UserDetails userDetails) {
//
//        return Jwts.builder()
//
//            .setSubject(userDetails.getUsername())
//
//            .setIssuedAt(new Date())
//
//            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//
//            .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
//
//            .compact();
//
//    }
// 
//    // Extract username from JWT
//
//    public String getUsernameFromJwt(String token) {
//
//        return Jwts.parserBuilder()
//
//            .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
//
//            .build()
//
//            .parseClaimsJws(token)
//
//            .getBody()
//
//            .getSubject();
//
//    }
// 
//    // Validate token format + signature + expiry
//
//    public boolean validateJwt(String token) {
//
//        try {
//
//            Jwts.parserBuilder()
//
//                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
//
//                .build()
//
//                .parseClaimsJws(token);
//
//            return true;
//
//        } catch (JwtException | IllegalArgumentException e) {
//
//            return false;
//
//        }
//
//    }
//
//}
//
// 
