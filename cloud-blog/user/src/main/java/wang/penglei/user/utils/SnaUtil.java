package wang.penglei.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;


/**
 * SHA(Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，
 * 被广泛地应用于电子商务等信息安全领域。虽然，SHA与MD5通过碰撞法都被破解了，
 * 但是SHA仍然是公认的安全加密算法，较之MD5更为安全
 *
 * @author Yuicon
 */
public class SnaUtil {

    private static final String KEY_SHA = "SHA-1";
    private static final String SALT = "db84622b-8514-4a9e-8090-c4fdfe2d2305";

    private static final Logger log = LoggerFactory.getLogger(SnaUtil.class);

    public static String digest(String inputStr) {
        BigInteger sha = null;

        byte[] inputData = (inputStr + SALT).getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            log.info("=======加密前的数据:" + inputStr + "   " + "SHA加密后:" + sha.toString(32));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(sha).toString(32);
    }

    public static void main(String args[]) {
        try {
            String inputStr = "简单加密";
            System.out.println(digest(inputStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
