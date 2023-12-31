package com.mindiqo.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
/**
 * JWT tool class
 */
public class JwtUtils {
    private static String signKey = "mindiqo";// Signature key
    private static Long expire = 43200000L; // expire time 12 hours
    /**
     * generate JWT token
     * @param claims JWT payload
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)// information (payload)
                .signWith(SignatureAlgorithm.HS256, signKey)// signature algorithm (header)
                .setExpiration(new Date(System.currentTimeMillis() + expire))// expire time
                .compact();
        return jwt;
    }
    /**
     * parse JWT token
     * @param jwt JWT token
     * @return JWT
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)// signature key
                .parseClaimsJws(jwt)// token
                .getBody();
        return claims;
    }

    /**
     * check if JWT token is expired
     * @param claims JWT
     * @return true if expired, false if not
     */
    public static boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date(System.currentTimeMillis()));
    }
    public static String generateMockJwt(Integer id){
        Map<String, Object> claims = Map.of(
                "id", 1,
                "username", "admin",
                "avatar", "https://avatars.githubusercontent.com/u/55942632?v=4"
        );
        String token = JwtUtils.generateJwt(claims);
        return token;
    }
}