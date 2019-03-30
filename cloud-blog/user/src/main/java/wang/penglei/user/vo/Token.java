package wang.penglei.user.vo;

import model.Account;
import utils.JwtUtils;

/**
 * @author Yuicon
 */
public class Token {

    private String accessToken;

    private String refreshToken;

    private String username;

    public static Token build(Account user) {
        Token token = new Token();
        token.setAccessToken(JwtUtils.buildToken(user));
        token.setRefreshToken(JwtUtils.buildRefreshToken(user));
        token.setUsername(user.getUsername());
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
