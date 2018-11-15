package wang.penglei.user.vo;

import model.User;
import utils.JwtUtils;

/**
 * @author Yuicon
 */
public class Token {

    private String accessToken;

    private String refreshToken;

    public static Token build(User user) {
        Token token = new Token();
        token.accessToken =  JwtUtils.buildToken(user);
        token.refreshToken =  JwtUtils.buildRefreshToken(user);
        return token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
