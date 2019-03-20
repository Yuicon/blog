package wang.penglei.user.vo;

import model.Account;
import utils.JwtUtils;

/**
 * @author Yuicon
 */
public class Token {

    private String accessToken;

    private String refreshToken;

    public static Token build(Account user) {
        Token token = new Token();
        token.setAccessToken(JwtUtils.buildToken(user));
        token.setRefreshToken(JwtUtils.buildRefreshToken(user));
        return token;
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
