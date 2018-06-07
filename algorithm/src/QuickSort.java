import java.util.Arrays;
import java.util.List;

/**
 * @author Yuicon
 * @Date 2018/6/5
 */
public class QuickSort {

    /**
     * 待排序的列表
     */
    private static List<Integer> list = Arrays.asList(9, 4, 1, 5, 7, 2, 8, 6, 3, 9, 2);

    public static void main(String[] args) {
        segmentation(0, list.size() - 1);
        System.out.println(list);
    }

    /**
     * 切分
     *
     * @param s 开始元素索引
     * @param e 结束元素索引
     */
    private static void segmentation(int s, int e) {
        if (s > e) {
            return;
        }
        // 取起点元素为基准数
        int temp = list.get(s);
        int i = s, j = e;
        // 将右边小于基准数的元素和左边大于基准数的元素交换,最终将基准数和相交点交换,得到一个基准数右边的元素都大于它,左边的元素都小于它的数组.
        while (i != j) {
            while (i < j && list.get(j) >= temp) {
                j--;
            }
            while (i < j && list.get(i) <= temp) {
                i++;
            }
            if (i < j) {
                int t = list.get(i);
                list.set(i, list.get(j));
                list.set(j, t);
            }
        }
        list.set(s, list.get(i));
        list.set(i, temp);
        // 继续切分基准数左边的元素
        segmentation(s, i - 1);
        // 继续切分基准数右边的元素
        segmentation(i + 1, e);
    }

}
