package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import model.Account;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yuicon
 */
public class JwtUtils {

    private static String key = "46d3a3c773f1401c9dbb2397e15163fa46d3a3c773f1401c9dbb2397e15163fa";
    private static volatile Key signKey;

    private static Key getKey() {
        if (signKey == null) {
            synchronized (JwtUtils.class) {
                if (signKey == null) {
                    byte[] encodedKey = Base64.getDecoder().decode(key);
                    signKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA512");
                }
            }
        }
        return signKey;
    }

    public static String buildToken(Account account) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 20);
        return Jwts.builder()
                .setIssuer("Yuicon")
                .setSubject(account.getUsername())
                .setIssuedAt(new Date())
                .setAudience("human")
                .setExpiration(Date.from(calendar.toInstant()))
                .signWith(getKey())
                .claim("id", account.getId())
                .claim("email", account.getEmail())
                .compact();
    }

    public static String buildRefreshToken(Account account) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        return Jwts.builder()
                .setIssuer("Yuicon")
                .setSubject(String.valueOf(account.getId()))
                .setIssuedAt(new Date())
                .setAudience("human")
                .setExpiration(Date.from(calendar.toInstant()))
                .signWith(getKey())
                .compact();
    }

    public static Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
    }

}
