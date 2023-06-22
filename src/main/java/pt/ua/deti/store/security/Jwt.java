package pt.ua.deti.store.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

public class Jwt {
    @Value("${jwt.secret}")
    private static String secret;

    public static boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getSubject(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public static String generateToken(UserDetailedView user) {
        return Jwts.builder().setSubject(user.getUsername()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
