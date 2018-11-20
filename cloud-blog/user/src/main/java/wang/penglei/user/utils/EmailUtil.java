package wang.penglei.user.utils;

/**
 * @author Yuicon
 */
public class EmailUtil {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public static boolean check(String email) {
        return email.matches(EMAIL_REGEX);
    }

}
