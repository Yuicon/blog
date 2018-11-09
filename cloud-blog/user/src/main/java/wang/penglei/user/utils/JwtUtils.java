package wang.penglei.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wang.penglei.user.model.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yuicon
 */
@Component
public class JwtUtils {

    @Value("jwt.key")
    private String key;
    private volatile Key signKey;

    private Key getKey() {
        if (signKey == null) {
            synchronized (this) {
                if (signKey == null) {
                    byte[] encodedKey = Base64.getDecoder().decode(key);
                    signKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
                }
            }
        }
        return signKey;
    }

    public final String buildToken(User user) {
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

    public final Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
    }

}
