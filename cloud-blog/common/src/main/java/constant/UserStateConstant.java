package constant;

import java.util.Arrays;

public class UserStateConstant {

    /**
     * 未激活
     */
    public static final int INACTIVE = 0;
    /**
     * 正常
     */
    public static final int ALRIGHT = 1;
    /**
     * 已删除
     */
    public static final int DELETED = 2;

    public static boolean exist(int state) {
        return state == INACTIVE || state == ALRIGHT || state == DELETED;
    }

}
