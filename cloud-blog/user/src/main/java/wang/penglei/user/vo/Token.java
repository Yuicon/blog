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
        token.setAccessToken(JwtUtils.buildToken(user));
        token.setRefreshToken(JwtUtils.buildRefreshToken(user));
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
