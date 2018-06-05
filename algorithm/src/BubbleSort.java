import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/4
 */
public class BubbleSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        int tmp;
        for (int i = 0; i < list.size() - 1; i++) {
            // 将未排序的元素相邻两两比较,并交换位置
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
        System.out.println(list);
    }

}
