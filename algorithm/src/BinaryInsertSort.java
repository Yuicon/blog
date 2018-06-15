import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/15
 */
public class BinaryInsertSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        int tmp;
        for (int i = 1; i < list.size(); i++) {
            //保存当前位置 i 的元素，其中[0, i - 1]已经有序
            tmp = list.get(i);
            int mid, left = 0, right = i - 1;
            //用二分法找合适的位置
            while (left <= right) {
                mid = (left + right) / 2;
                if (tmp < list.get(mid)) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            //在已排序的元素中将大于 i 的元素都后移一位
            for (int j = i - 1; j >= left; j--) {
                list.set(j + 1, list.get(j));
            }
            //插入到合适的位置
            list.set(left, tmp);
        }
        System.out.println(list);
    }

}
