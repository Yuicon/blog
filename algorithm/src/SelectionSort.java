import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/4
 */
public class SelectionSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> arr = Arrays.asList(9, 1, 4, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        int minIndex;
        int tmp;
        for (int i = 0; i < arr.size() - 1; i++) {
            minIndex = i;
            // 找出未排序的数据中最小的数据
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(j) < arr.get(minIndex)) {
                    // 将 minIndex 指向最小数据的索引
                    minIndex = j;
                }
            }
            // 交换数据
            tmp = arr.get(i);
            arr.set(i, arr.get(minIndex));
            arr.set(minIndex, tmp);
        }
        System.out.println(arr);
    }

}
