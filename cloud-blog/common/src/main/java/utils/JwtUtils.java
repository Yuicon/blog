package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import model.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author Yuicon
 */
public class JwtUtils {

    private static String key = "123456";
    private static volatile Key signKey;

    private static Key getKey() {
        if (signKey == null) {
            synchronized (JwtUtils.class) {
                if (signKey == null) {
                    byte[] encodedKey = Base64.getDecoder().decode(key);
                    signKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
                }
            }
        }
        return signKey;
    }

    public static String buildToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 20);
        return Jwts.builder()
                .setIssuer("Yuicon")
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setAudience("human")
                .setExpiration(Date.from(calendar.toInstant()))
                .signWith(getKey())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .compact();
    }

    public static String buildRefreshToken(User user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        return Jwts.builder()
                .setIssuer("Yuicon")
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setAudience("human")
                .setExpiration(Date.from(calendar.toInstant()))
                .signWith(getKey())
                .compact();
    }

    public static Jws<Claims> parseToken(String token) throws Exception{
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
    }

}
