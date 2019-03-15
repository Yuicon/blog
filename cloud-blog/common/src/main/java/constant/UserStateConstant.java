package constant;

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

    public static String instruction(int state) {
        switch (state) {
            case 0:
                return "未激活";
            case 1:
                return "正常";
            case 2:
                return "已删除";
            default:
                return "状态异常";
        }
    }

}
