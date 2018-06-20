import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/20
 */
public class ShellSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        //增量gap，并逐步缩小增量
        for (int gap = list.size() / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < list.size(); i++) {
                int j = i;
                //插入排序操作,从每个分组的第二个元素开始
                while (j - gap >= 0 && list.get(j) < list.get(j - gap)) {
                    swap(j, j - gap);
                    j -= gap;
                }
            }
        }
    }

    private static void swap(int i, int j) {
        list.set(i, list.get(i) + list.get(j));
        list.set(j, list.get(i) - list.get(j));
        list.set(i, list.get(i) - list.get(j));
    }

}
