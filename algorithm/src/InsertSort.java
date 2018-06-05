import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/5
 */
public class InsertSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        int tmp;
        for (int i = 1; i < list.size(); i++) {
            //保存当前位置 i 的元素，其中[0, i - 1]已经有序
            tmp = list.get(i);
            int j;
            //在已排序的元素中将小于 i 的元素都后移一位
            for (j = i; j > 0 && tmp < list.get(j - 1); j--) {
                int t = list.get(j);
                list.set(j, list.get(j - 1));
                list.set(j - 1, t);
            }
            //插入到合适的位置
            list.set(j, tmp);
        }
        System.out.println(list);
    }

}
